package com.example.appinterface

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.producto.ProductoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaProductosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerProducto)
        recyclerView.layoutManager = LinearLayoutManager(this)

        RetrofitInstance.productosApi.listarProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    recyclerView.adapter = ProductoAdapter(response.body()!!)
                } else {
                    Toast.makeText(this@ListaProductosActivity, "No hay productos disponibles", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Toast.makeText(this@ListaProductosActivity, "Error de conexión: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
