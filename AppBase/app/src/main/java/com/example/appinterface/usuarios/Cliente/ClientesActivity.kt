package com.example.appinterface.usuarios.Cliente

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Adapter.ClienteAdapter
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerClientes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.btnVolverClientes).setOnClickListener {
            finish()
        }






        RetrofitInstance.empleadosApi.obtenerClientes().enqueue(object : Callback<List<cliente>> {
            override fun onResponse(call: Call<List<cliente>>, response: Response<List<cliente>>) {
                val clientes = response.body()
                if (response.isSuccessful && !clientes.isNullOrEmpty()) {
                    recyclerView.adapter = ClienteAdapter(clientes.toMutableList(), this@ClientesActivity)
                } else {
                    Toast.makeText(this@ClientesActivity, "No hay clientes disponibles", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<cliente>>, t: Throwable) {
                Toast.makeText(this@ClientesActivity, "Error de conexión: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })
    }
}