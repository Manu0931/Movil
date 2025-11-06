package com.example.appinterface.Api

import com.example.appinterface.Empleado
import com.example.appinterface.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServicesKotlin {

    @GET("empleados")
    fun getEmpleados(): Call<List<Empleado>>

    @POST("empleados")
    fun crearEmpleado(@Body empleado: Empleado): Call<String>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}
