package com.example.appinterface.logueo

import com.example.appinterface.Api.RetrofitInstance
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.MainActivity
import com.example.appinterface.R
import com.example.appinterface.usuarios.PanelCliente
import com.example.appinterface.usuarios.AdminPanelActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private val api = RetrofitInstance.empleadosApi

    private lateinit var btnTogglePassword: ImageButton

    private var passwordVisible = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnTogglePassword = findViewById(R.id.btnTogglePassword)
        findViewById<Button>(R.id.btnLogin).setOnClickListener { doLogin() }

        findViewById<Button>(R.id.btnRegresar).setOnClickListener {
            finish()
        }
        btnTogglePassword.setOnClickListener {
            passwordVisible = !passwordVisible

            if (passwordVisible) {
                etPassword.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                btnTogglePassword.setImageResource(R.drawable.ic_eye)
            } else {
                etPassword.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                btnTogglePassword.setImageResource(R.drawable.ic_eye_off)
            }

            etPassword.setSelection(etPassword.text.length)
        }



        findViewById<TextView>(R.id.tvForgotPassword).setOnClickListener {
            startActivity(Intent(this, RecuperarPasswordActivity::class.java))
        }

        findViewById<TextView>(R.id.tvRegister).setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    private fun doLogin() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show()
            return
        }


        val request = LoginRequest(email, password)

        api.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val role = response.body()?.rol ?: "cliente"
                    val mensaje = response.body()?.mensaje ?: "Inicio exitoso"
                    val nombreUsuario = response.body()?.nombre ?: "Usuario"

                    Toast.makeText(this@LoginActivity, mensaje, Toast.LENGTH_SHORT).show()


                    val sharedPref = getSharedPreferences("SesionUsuario", MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putString("email", email)
                    editor.putString("rol", role)
                    editor.putBoolean("logueado", true)
                    editor.apply()

                    when (role.lowercase()) {
                        "administrador", "empleado" -> {
                            val intent = Intent(this@LoginActivity, AdminPanelActivity::class.java)
                            intent.putExtra("nombre", nombreUsuario)
                            startActivity(intent)
                        }
                        else -> {
                            val intent = Intent(this@LoginActivity, PanelCliente::class.java)
                            intent.putExtra("nombreCliente", nombreUsuario)
                            startActivity(intent)
                        }
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
