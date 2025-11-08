package com.example.appinterface.Api

import com.example.appinterface.logueo.LoginRequest
import com.example.appinterface.usuarios.Empleado
import com.example.appinterface.logueo.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServicesKotlin {

    @GET("empleados")
    fun getEmpleados(): Call<List<Empleado>>

    @POST("empleados")
    fun crearEmpleado(@Body empleado: Empleado): Call<String>

    @POST("auth/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>
}
