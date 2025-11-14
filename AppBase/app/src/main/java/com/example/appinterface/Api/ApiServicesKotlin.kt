package com.example.appinterface.Api

import com.example.appinterface.Cupones.Cupon
import com.example.appinterface.logueo.LoginRequest
import com.example.appinterface.usuarios.Empleado
import com.example.appinterface.logueo.LoginResponse
import com.example.appinterface.producto.Producto
import com.example.appinterface.logueo.registro.RegistroResponse
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
        @Body empleado: Empleado
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

    @GET("productos")
    fun listarProductos(): Call<List<Producto>>

    @Multipart
    @POST("/productos/crear")
    fun crearProducto(
        @Part("producto") productoJson: RequestBody,
        @Part imagen: MultipartBody.Part
    ): Call<Producto>

    @PUT("productos/{id}")
    fun actualizarProducto(
        @Path("id") id: Int,
        @Body productos: Producto
    ): Call<String>

    @DELETE("/productos/{id}")
    fun eliminarProducto(@Path("id") id: Int): Call<String>

    @GET("envio")
    fun getEnvio(): Call<List<Envio>>

    @GET("pedido")
    fun getPedido(): Call<List<Pedido>>
}



