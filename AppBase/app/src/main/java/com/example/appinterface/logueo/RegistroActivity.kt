package com.example.appinterface.logueo

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import com.example.appinterface.logueo.registro.RegistroResponse
import com.example.appinterface.modelos.RegistroRequest
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

            RetrofitInstance.empleadosApi.registrarCliente(request).enqueue(object : Callback<RegistroResponse> {
                override fun onResponse(call: Call<RegistroResponse>, response: Response<RegistroResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(this@RegistroActivity, "Registro exitoso", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegistroActivity, response.body()?.mensaje ?: "Error al registrar", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<RegistroResponse>, t: Throwable) {
                    Toast.makeText(this@RegistroActivity, "Error: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
