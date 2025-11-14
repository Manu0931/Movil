package com.example.appinterface.usuarios

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.R

class InventarioActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventario)

        val btnIrCupones = findViewById<Button>(R.id.btnIrCupones)

        btnIrCupones.setOnClickListener {
            val intent = Intent(this, CuponesActivity::class.java)
            startActivity(intent)
        }
    }
}
