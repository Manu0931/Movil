package com.example.appinterface.Cupones

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Adapter.Cupon.CuponAdapter
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaCuponesActivity : AppCompatActivity() {

    private lateinit var recyclerCupones: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_cupones)

        recyclerCupones = findViewById(R.id.recyclerCupones)
        recyclerCupones.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.btnVolver).setOnClickListener {
            finish()
        }

        obtenerCupones()
    }

    private fun obtenerCupones() {
        RetrofitInstance.empleadosApi.getCupones()
            .enqueue(object : Callback<List<Cupon>> {
                override fun onResponse(call: Call<List<Cupon>>, response: Response<List<Cupon>>) {
                    if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                        recyclerCupones.adapter =
                            CuponAdapter(
                                context = this@ListaCuponesActivity,
                                listaCupones = response.body()!!.toMutableList()
                            )
                    } else {
                        Toast.makeText(this@ListaCuponesActivity, "No hay cupones disponibles", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Cupon>>, t: Throwable) {
                    Toast.makeText(this@ListaCuponesActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
    }
}
