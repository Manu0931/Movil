package com.example.appinterface.producto

import android.content.Intent
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

                    // Aquí se crea el adapter con editar + eliminar
                    val adapter = ProductoAdapter(
                        listaProductos,
                        onEditar = { producto ->
                            val intent = Intent(this@ProductosActivity, EditarProductoActivity::class.java)
                            intent.putExtra("producto", producto) // Asegúrate de que Producto implemente Serializable o Parcelable
                            startActivity(intent)
                        },
                        onEliminar = { producto ->
                            eliminarProducto(producto)
                        }
                    )

                    recyclerView.adapter = adapter

                } else {
                    Toast.makeText(
                        this@ProductosActivity,
                        "Error al obtener productos: ${response.code()}",
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

    private fun eliminarProducto(producto: Producto) {
        RetrofitInstance.productosApi.eliminarProducto(producto.idProducto ?: 0)


        RetrofitInstance.productosApi.eliminarProducto(producto.idProducto ?: 0)
            .enqueue(object : Callback<Producto> {
                override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ProductosActivity, "Producto eliminado", Toast.LENGTH_SHORT).show()
                        listarProductos() // refresca la lista
                    } else {
                        Toast.makeText(this@ProductosActivity, "Error al eliminar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Producto>, t: Throwable) {
                    Toast.makeText(this@ProductosActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

    }
}

