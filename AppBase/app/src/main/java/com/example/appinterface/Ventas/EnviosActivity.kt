package com.example.appinterface.Ventas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Adapter.EnvioAdapter
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnviosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_envios)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerEnvios)
        recyclerView.layoutManager = LinearLayoutManager(this)


        RetrofitInstance.envioApi.getEnvio().enqueue(object : Callback<List<Envio>> {
            override fun onResponse(call: Call<List<Envio>>, response: Response<List<Envio>>) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    recyclerView.adapter = EnvioAdapter(response.body()!!)
                } else {
                    Toast.makeText(
                        this@EnviosActivity,
                        "No hay envíos disponibles",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Envio>>, t: Throwable) {
                Toast.makeText(
                    this@EnviosActivity,
                    "Error de conexión: ${t.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

}
