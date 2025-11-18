package com.example.appinterface.Cupones

import android.content.Intent
import android.os.Bundle
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

                if (response.isSuccessful && !response.body().isNullOrEmpty()) {

                    val lista = response.body()!!
                    adapter = CuponAdapter(
                        listaCupones = lista,
                        onEditar = { cupon ->
                            // Abrir Activity para editar el cupón
                            val intent = Intent(
                                this@ListaCuponesActivity,
                                EditarCuponActivity::class.java
                            )

                            intent.putExtra("id", cupon.ID_Cupon)
                            intent.putExtra("codigo", cupon.codigo)
                            intent.putExtra("descuento", cupon.descuento)
                            intent.putExtra("fecha", cupon.fecha_Expiracion)

                            startActivity(intent)
                        },
                        onEliminar = { cupon ->
                            // Llamar al método para eliminar el cupón
                            eliminarCupon(cupon.ID_Cupon)
                        }
                    )

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

    private fun eliminarCupon(id: Int) {

        val call = RetrofitInstance.empleadosApi.eliminarCupon(id)

        call.enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@ListaCuponesActivity,
                        "Cupón eliminado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Recargar la lista para que desaparezca del Recycler
                    obtenerCupones()

                } else {
                    Toast.makeText(
                        this@ListaCuponesActivity,
                        "No se pudo eliminar el cupón",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@ListaCuponesActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
