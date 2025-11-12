package com.example.appinterface.logueo

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import com.example.appinterface.logueo.registro.RegistroResponse
import com.example.appinterface.modelos.RegistroRequest
import com.example.appinterface.usuarios.cliente
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val etNombre = findViewById<EditText>(R.id.etNombreRegistro)
        val etDocumento = findViewById<EditText>(R.id.etDocumentoRegistro)
        val etTelefono = findViewById<EditText>(R.id.etTelefonoRegistro)
        val etEmail = findViewById<EditText>(R.id.etEmailRegistro)
        val etPassword = findViewById<EditText>(R.id.etPasswordRegistro)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val documento = etDocumento.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (nombre.isEmpty() || documento.isEmpty() || telefono.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = RegistroRequest(nombre, correo = email, contrasena = password, documento = documento, telefono = telefono)

            RetrofitInstance.empleadosApi.registrarCliente(request)
                .enqueue(object : Callback<cliente> {
                    override fun onResponse(call: Call<cliente>, response: Response<cliente>) {
                        if (response.isSuccessful) {
                            val clienteCreado = response.body()
                            if (clienteCreado != null) {
                                Toast.makeText(
                                    this@RegistroActivity,
                                    "Cliente registrado correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@RegistroActivity,
                                    "No se recibió información del cliente",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this@RegistroActivity,
                                "Error al registrar cliente (${response.code()})",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<cliente>, t: Throwable) {
                        Toast.makeText(
                            this@RegistroActivity,
                            "Error: ${t.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

        }
    }
}
