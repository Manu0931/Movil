package com.example.appinterface.Api

import com.example.appinterface.Cupones.Cupon
import com.example.appinterface.logueo.LoginRequest
import com.example.appinterface.usuarios.Empleado
import com.example.appinterface.logueo.LoginResponse
import com.example.appinterface.producto.Producto
import com.example.appinterface.usuarios.cliente
import com.example.appinterface.modelos.RegistroRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import com.example.appinterface.Ventas.Envio
import com.example.appinterface.Ventas.Pedido


interface ApiServicesKotlin {

    @GET("empleados")
    fun getEmpleados(): Call<List<Empleado>>

    @POST("empleados")
    fun crearEmpleado(@Body empleado: Empleado): Call<Empleado>

    @PUT("empleados/{id}")
    fun actualizarEmpleado(
        @Path("id") id: Int,
        @Body empleado: Empleado
    ): Call<Empleado>

    @DELETE("empleados/{id}")
    fun eliminarEmpleado(
        @Path("id") id: Int
    ): Call<Empleado>


    @GET("clientes")
    fun obtenerClientes(): Call<List<cliente>>

    @POST("clientes")
    fun registrarCliente(@Body request: RegistroRequest): Call<cliente>


    @PUT("clientes/{id}")
    fun actualizarClientes(
        @Path("id") id: Int,
        @Body cliente: cliente
    ): Call<cliente>

    @DELETE("clientes/{id}")
    fun eliminarClientes(
        @Path("id") id: Int
    ): Call<cliente>


    @POST("auth/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @GET("cupon")
    fun getCupones(): Call<List<Cupon>>

    @PUT("cupon/{id}")
    fun actualizarCupon(
        @Path("id") idCupon: Int,
        @Body cupon: Cupon
    ): Call<Void>


    @DELETE("cupon/{id}")
    fun eliminarCupon(
        @Path("id") id: Int,
    ): Call<Cupon>

    @GET("productos")
    fun listarProductos(): Call<List<Producto>>

    @Multipart
    @POST("productos/insertar")
    fun insertarProducto(
        @Part("nombre") nombre: RequestBody,
        @Part("descripcion") descripcion: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("idProveedor") proveedor: RequestBody,
        @Part("estado") estado: RequestBody,
        @Part imagen: MultipartBody.Part?
    ): Call<Producto>


    @Multipart
    @POST("productos/actualizar/{id}")
    fun actualizarProducto(
        @Path("id") id: Int,
        @Part("nombre") nombre: RequestBody,
        @Part("descripcion") descripcion: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("idProveedor") proveedor: RequestBody,
        @Part("estado") estado: RequestBody,
        @Part imagen: MultipartBody.Part?
    ): Call<Producto>


    @DELETE("/productos/eliminar/{id}")
    fun eliminarProducto(@Path("id") id: Int): Call<Producto>

    @GET("envio")
    fun getEnvio(): Call<List<Envio>>

    @POST("envio")
    fun crearEnvio(@Body envio: Envio): Call<Envio>

    @GET("pedido")
    fun getPedido(): Call<List<Pedido>>

    @POST("pedido")
    fun crearPedido(@Body pedido: Pedido): Call<Pedido>

}



