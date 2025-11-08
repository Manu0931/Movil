package com.example.appinterface


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.appinterface.Api.RetrofitInstance

class CuponesActivity : AppCompatActivity() {

    private lateinit var txtCupones: TextView
    private lateinit var btnMostrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cupones)

        txtCupones = findViewById(R.id.txtCupones)
        btnMostrar = findViewById(R.id.btnMostrar)

        btnMostrar.setOnClickListener {
            cargarCupones()
        }
    }

    private fun cargarCupones() {
        val call = RetrofitInstance.api2kotlin.getCupones()

        call.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                if (response.isSuccessful) {
                    val lista = response.body()
                    if (!lista.isNullOrEmpty()) {
                        val builder = StringBuilder()
                        for (linea in lista) {
                            builder.append(linea).append("\n\n")
                        }
                        txtCupones.text = builder.toString()
                    } else {
                        txtCupones.text = "No hay cupones registrados."
                    }
                } else {
                    txtCupones.text = "Error en respuesta: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                txtCupones.text = "Error de conexión: ${t.message}"
            }
        })
    }

}
