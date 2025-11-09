package com.example.appinterface.Api

import com.example.appinterface.LoginRequest
import com.example.appinterface.Empleado
import com.example.appinterface.LoginResponse
import com.example.appinterface.Producto
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

    @GET("productos")
    fun listarProductos(): Call<List<Producto>>
}
