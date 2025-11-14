package com.example.appinterface.Cupones

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Adapter.Cupon.CuponAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.R
import com.example.appinterface.Api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaCuponesActivity : AppCompatActivity() {

    private lateinit var recyclerCupones: RecyclerView
    private lateinit var adapter: CuponAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_cupones)

        recyclerCupones = findViewById(R.id.recyclerCupones)
        recyclerCupones.layoutManager = LinearLayoutManager(this)

        obtenerCupones()
    }

    private fun obtenerCupones() {
        val call = RetrofitInstance.empleadosApi.getCupones()

        call.enqueue(object : Callback<List<Cupon>> {
            override fun onResponse(call: Call<List<Cupon>>, response: Response<List<Cupon>>) {
                val lista = response.body()!!
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    adapter = CuponAdapter(lista)
                    recyclerCupones.adapter = adapter
                } else {
                    Toast.makeText(
                        this@ListaCuponesActivity,
                        "No hay cupones registrados.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Cupon>>, t: Throwable) {
                Toast.makeText(
                    this@ListaCuponesActivity,
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
