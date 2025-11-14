package com.example.appinterface.logueo

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.R

class RecuperarPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_password)

        val etEmail = findViewById<EditText>(R.id.etEmailRecuperar)
        val btnEnviar = findViewById<Button>(R.id.btnEnviarCodigo)

        btnEnviar.setOnClickListener {
            val email = etEmail.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(this, "Ingresa tu correo", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Código de verificación enviado a $email", Toast.LENGTH_LONG).show()
                // Aquí luego conectas el envío real con Retrofit
            }
        }
    }
}
