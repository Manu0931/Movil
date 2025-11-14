package com.example.appinterface.Ventas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PedidoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerPedidos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        RetrofitInstance.pedidoApi.getPedido().enqueue(object : Callback<List<Pedido>> {
            override fun onResponse(call: Call<List<Pedido>>, response: Response<List<Pedido>>) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    recyclerView.adapter = PedidoAdapter(response.body()!!)
                } else {
                    Toast.makeText(
                        this@PedidoActivity,
                        "No hay pedidos disponibles",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Pedido>>, t: Throwable) {
                Toast.makeText(
                    this@PedidoActivity,
                    "Error de conexión: ${t.localizedMessage}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}

