package com.example.appinterface.Cupones

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CrearCuponActivity : AppCompatActivity() {

    private lateinit var etCodigo: EditText
    private lateinit var etDescuento: EditText
    private lateinit var etFecha: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cupon_form)

        etCodigo = findViewById(R.id.etcodigo)
        etDescuento = findViewById(R.id.etdescuento)
        etFecha = findViewById(R.id.etfecha)

        val btnVolver = findViewById<ImageButton>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun crearCupon(v: android.view.View) {

        val codigo = etCodigo.text.toString().trim()
        val descuento = etDescuento.text.toString().trim()
        val fecha = etFecha.text.toString().trim()

        if (codigo.isEmpty() || descuento.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val cupon = Cupon(
            ID_Cupon = 0,
            codigo = codigo,
            descuento = descuento.toInt(),
            fecha_Expiracion = fecha
        )

        RetrofitInstance.empleadosApi.agregarCupon(cupon)
            .enqueue(object : Callback<Cupon> {
                override fun onResponse(call: Call<Cupon>, response: Response<Cupon>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@CrearCuponActivity,
                            "Cupón creado correctamente",
                            Toast.LENGTH_LONG
                        ).show()
                        limpiar()
                    } else {
                        Toast.makeText(
                            this@CrearCuponActivity,
                            "Error al crear cupón (${response.code()})",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Cupon>, t: Throwable) {
                    Toast.makeText(
                        this@CrearCuponActivity,
                        "Error de conexión: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }



    private fun limpiar() {
        etCodigo.text.clear()
        etDescuento.text.clear()
        etFecha.text.clear()
    }
}
