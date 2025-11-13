package com.example.appinterface.Api

import com.example.appinterface.logueo.LoginRequest
import com.example.appinterface.usuarios.Empleado
import com.example.appinterface.logueo.LoginResponse
import com.example.appinterface.logueo.registro.RegistroResponse
import com.example.appinterface.usuarios.cliente
import com.example.appinterface.Cupones.Cupon
import com.example.appinterface.modelos.RegistroRequest
import retrofit2.Call
import retrofit2.http.*

interface ApiServicesKotlin {

    @GET("empleados")
    fun getEmpleados(): Call<List<Empleado>>

    @POST("empleados")
    fun crearEmpleado(@Body empleado: Empleado): Call<String>

    @FormUrlEncoded
    @POST("login")
    @PUT("empleados/{id}")
    fun actualizarEmpleado(
        @Path("id") id: Int,
        @Body empleado: Empleado
    ): Call<String>

    @DELETE("empleados/{id}")
    fun eliminarEmpleado(
        @Path("id") id: Int
    ): Call<String>

    @GET("clientes")
    fun obtenerClientes(): Call<List<cliente>>

    @POST("clientes")
    fun registrarCliente(@Body request: RegistroRequest): Call<RegistroResponse>


    @PUT("clientes/{id}")
    fun actualizarClientes(
        @Path("id") id: Int,
        @Body empleado: Empleado
    ): Call<String>

    @DELETE("clientes/{id}")
    fun eliminarClientes(
        @Path("id") id: Int
    ): Call<String>

    @GET("cupon")
    fun getCupones(): Call<List<Cupon>>


    @POST("auth/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>


}
