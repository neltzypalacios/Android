package com.barbaro.mitiendieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.barbaro.mitiendieapp.API.MiTiendiAppService;
import com.barbaro.mitiendieapp.models.Producto;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FormAgregarActivity extends AppCompatActivity {

    private EditText inputNombre;
    private EditText inputDes;
    private EditText inputCantidad;
    private EditText inputPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_agregar);

        // Proceso de inflado
        inputNombre = findViewById(R.id.inputNombre);
        inputDes = findViewById(R.id.inputDes);
        inputCantidad = findViewById(R.id.inputCantidad);
        inputPrecio = findViewById(R.id.inputPrecio);

        Button btnAgregar = findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperarDatos();
            }
        });
    }

    private void recuperarDatos() {
        String nombre = inputNombre.getText().toString();
        String descripcion = inputDes.getText().toString();
        String cantidad = inputCantidad.getText().toString();
        String precio = inputPrecio.getText().toString();

        if(!nombre.isEmpty() &&
                !cantidad.isEmpty() && !precio.isEmpty()){

            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setCantidad(Integer.parseInt(cantidad));
            producto.setPrecio(Float.parseFloat(precio));

            mandarDatosWebservices(producto);
        }else{
            Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void mandarDatosWebservices(Producto producto) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.81.69:8080/CRUDRest/")
                .build();
        Call<ResponseBody> call = retrofit.create(MiTiendiAppService.class)
                .crearProducto(producto.getDescripcion(),producto.getNombre(), producto.getPrecio(), producto.getCantidad());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if (response.isSuccessful())                    {
                        ResponseBody body = response.body();

                        String mensaje =body.string();
                        mostrarMensaje(mensaje);
                    }  else {
                        response.errorBody();
                        mostrarMensaje("");
                    }
                }catch (IOException e)                    {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(t != null){
                    mostrarMensaje(t.getMessage());
                }
            }
        });
    }
    private void mostrarMensaje(String mensaje)
    {
        Toast.makeText(this,mensaje, Toast.LENGTH_SHORT).show();
    }

}