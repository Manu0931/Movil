package com.example.appinterface.Api


import com.example.appinterface.Cupon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiServicesKotlin {
    @GET("/empleados")
    fun getEmpleados(): Call<List<Empleado>>
    @POST("/empleados")
    fun crearEmpleado(@Body empleado: Empleado): Call<String>
    @GET("/cupon")
    fun getCupones(): Call<List<String>>
}