package com.example.appinterface.producto

import android.os.Bundle
import android.util.Log
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

    private var id_Producto: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos)

        id_Producto = intent.getIntExtra("id_Producto", 0)
        Log.d("EDITAR", "ID recibido en activity → $id_Producto")


        val recyclerView = findViewById<RecyclerView>(R.id.RecyProductos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.btnVolverProductos).setOnClickListener {
            finish()
        }


        RetrofitInstance.productosApi.listarProductos()
            .enqueue(object : Callback<List<Producto>> {
                override fun onResponse(
                    call: Call<List<Producto>>,
                    response: Response<List<Producto>>
                ) {
                    val productos = response.body()

                    if (response.isSuccessful && !productos.isNullOrEmpty()) {

                        recyclerView.adapter =
                            ProductoAdapter(productos.toMutableList(), this@ProductosActivity)

                    } else {
                        Toast.makeText(
                            this@ProductosActivity,
                            "No hay productos disponibles",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                    Toast.makeText(
                        this@ProductosActivity,
                        "Error de conexión: ${t.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}
