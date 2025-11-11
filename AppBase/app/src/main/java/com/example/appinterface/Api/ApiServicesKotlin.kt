package com.example.appinterface.Api

import com.example.appinterface.logueo.LoginRequest
import com.example.appinterface.usuarios.Empleado
import com.example.appinterface.logueo.LoginResponse
import com.example.appinterface.logueo.registro.RegistroResponse
import com.example.appinterface.modelos.Cliente
import com.example.appinterface.modelos.RegistroRequest
import retrofit2.Call
import retrofit2.http.*

interface ApiServicesKotlin {

    @GET("empleados")
    fun getEmpleados(): Call<List<Empleado>>

    @GET("empleados/{id}")
    fun getEmpleadoById(@Path("id") id: Int): Call<Empleado>

    @POST("empleados")
    fun crearEmpleado(@Body empleado: Empleado): Call<String>

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
    fun obtenerClientes(): Call<List<Cliente>>

    @POST("clientes")
    fun registrarCliente(@Body request: RegistroRequest): Call<RegistroResponse>


    @POST("auth/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>
}
