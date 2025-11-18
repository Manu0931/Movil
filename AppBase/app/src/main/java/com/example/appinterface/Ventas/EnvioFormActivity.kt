package com.example.appinterface.Ventas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EnvioFormActivity : AppCompatActivity() {

    private lateinit var direccion: EditText
    private lateinit var metodo: EditText
    private lateinit var estado: EditText
    private lateinit var idPedido: EditText
    private lateinit var fechaEnvio: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_envio_form)

        direccion = findViewById(R.id.direccionEnvio)
        metodo = findViewById(R.id.metodoEnvio)
        estado = findViewById(R.id.estadoEnvio)
        idPedido = findViewById(R.id.idPedidoEnvio)
        fechaEnvio = findViewById(R.id.fechaEnvio)


        val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        fechaEnvio.setText(fechaActual)

        val btnVolver = findViewById<ImageButton>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun crearEnvio(v: View) {
        val envio = Envio(
            ID_Pedido = idPedido.text.toString().trim().toIntOrNull() ?: -1,
            direccionEnvio = direccion.text.toString().trim(),
            fechaEnvio = fechaEnvio.text.toString().trim(),
            metodoEnvio = metodo.text.toString().trim(),
            estadoEnvio = estado.text.toString().trim()
        )

        if (envio.ID_Pedido == -1 ||
            envio.direccionEnvio.isEmpty() ||
            envio.fechaEnvio.isEmpty() ||
            envio.metodoEnvio.isEmpty() ||
            envio.estadoEnvio.isEmpty()
        ) {
            Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        RetrofitInstance.envioApi.crearEnvio(envio)
            .enqueue(object : Callback<Envio> {
                override fun onResponse(call: Call<Envio>, data: Response<Envio>) {
                    if (data.isSuccessful) {
                        Toast.makeText(
                            this@EnvioFormActivity,
                            "Envio registrado correctamente",
                            Toast.LENGTH_LONG
                        ).show()

                        val intent = Intent(this@EnvioFormActivity, VentasActivity::class.java)
                        startActivity(intent)
                        finish()


                    } else {
                        Toast.makeText(
                            this@EnvioFormActivity,
                            "Error al registrar el envío: ${data.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Envio>, t: Throwable) {
                    Log.e("ERROR_ENVIO", "Fallo", t)
                    Toast.makeText(
                        this@EnvioFormActivity,
                        "Se cayó la vuelta: ${t.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
    }

    private fun limpiarCampos() {
        direccion.text.clear()
        metodo.text.clear()
        estado.text.clear()
        idPedido.text.clear()
    }
}

