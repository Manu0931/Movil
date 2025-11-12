package com.example.appinterface.usuarios

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import com.example.appinterface.modelos.RegistroRequest
import com.example.appinterface.logueo.registro.RegistroResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientesFormActivity : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var correo: EditText
    private lateinit var contrasena: EditText
    private lateinit var documento: EditText
    private lateinit var telefono: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_form)

        nombre = findViewById(R.id.nombre)
        correo = findViewById(R.id.correo)
        contrasena = findViewById(R.id.contrasena)
        documento = findViewById(R.id.documento)
        telefono = findViewById(R.id.telefono)

        val btnVolver = findViewById<ImageButton>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun crearCliente(v: View) {
        val request = RegistroRequest(
            nombre = nombre.text.toString().trim(),
            correo = correo.text.toString().trim(),
            contrasena = contrasena.text.toString().trim(),
            documento = documento.text.toString().trim(),
            telefono = telefono.text.toString().trim()
        )

        if (request.nombre.isEmpty() || request.correo.isEmpty() ||
            request.contrasena.isEmpty() || request.documento.isEmpty() ||
            request.telefono.isEmpty()
        ) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        RetrofitInstance.empleadosApi.registrarCliente(request)
            .enqueue(object : Callback<RegistroResponse> {
                override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@ClientesFormActivity,
                            "Cliente creado correctamente",
                            Toast.LENGTH_LONG
                        ).show()
                        limpiarCampos()
                    } else {
                        Toast.makeText(
                            this@ClientesFormActivity,
                            "Error al crear cliente: ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<RegistroResponse>, t: Throwable) {
                    Toast.makeText(
                        this@ClientesFormActivity,
                        "Fallo de conexión: ${t.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun limpiarCampos() {
        nombre.text.clear()
        correo.text.clear()
        contrasena.text.clear()
        documento.text.clear()
        telefono.text.clear()
    }
}
