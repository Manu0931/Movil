package com.example.appinterface.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val empleadosApi: ApiServicesKotlin by lazy {
        retrofit.create(ApiServicesKotlin::class.java)
    }

    val envioApi: ApiServicesKotlin by lazy {
        retrofit.create(ApiServicesKotlin::class.java)
    }
}
