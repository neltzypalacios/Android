package com.barbaro.mitiendieapp.API;

import com.barbaro.mitiendieapp.models.Producto;

import retrofit2.Call;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MiTiendiAppService
{
    @POST("producto")
    Call<ResponseBody> sendProducto(@Body Producto producto);

    @POST("producto/{id}/{nombre}/{precio}/{existencias}")
    Call<ResponseBody> crearProducto(@Path("id") String id,
                                        @Path("nombre") String nombre,
                                        @Path("precio") float precio,
                                        @Path("existencias") int existencias );

    @GET("producto")
    Call<ResponseBody> BuscarProductoPorNombre(@Query("nombre") String nombre);




}
