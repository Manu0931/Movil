package com.example.appinterface.producto

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Adapter.ProductoAdapter
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductosActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)


        recyclerView = findViewById(R.id.RecyProductos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        findViewById<ImageView>(R.id.btnVolverProductos).setOnClickListener {
            finish()
        }


        listarProductos()
    }


    private fun listarProductos() {
        val call = RetrofitInstance.productosApi.listarProductos()


        call.enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful && response.body() != null) {
                    val listaProductos = response.body()!!
                    recyclerView.adapter = ProductoAdapter(listaProductos)
                } else {
                    //Aquí agregamos el Toast con el código de error
                    Toast.makeText(
                        this@ProductosActivity,
                        "Error al obtener productos: ${response.code()} ${response.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Toast.makeText(
                    this@ProductosActivity,
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
