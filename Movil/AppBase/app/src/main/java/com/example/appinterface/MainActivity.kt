package com.example.appinterface

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al botón Cupones
        val btnCupones = findViewById<Button>(R.id.btnCupones)

        // Evento click para abrir CuponesActivity
        btnCupones.setOnClickListener {
            val intent = Intent(this, CuponesActivity::class.java)
            startActivity(intent)
        }
    }
}
