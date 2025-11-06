package com.example.appinterface

import com.example.appinterface.Api.RetrofitInstance
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private val api = RetrofitInstance.empleadosApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        findViewById<Button>(R.id.btnLogin).setOnClickListener { doLogin() }
    }

    private fun doLogin() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        api.login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val role = response.body()?.rol ?: "cliente"
                    Toast.makeText(this@LoginActivity, "Bienvenido ${response.body()?.nombre}", Toast.LENGTH_SHORT).show()
                    // ejemplo de navegación según rol
                    when (role.lowercase()) {
                        "administrador" -> startActivity(Intent(this@LoginActivity, EmpleadosActivity::class.java))
                        else -> startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    }
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Credenciales inválidas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error de conexión: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
