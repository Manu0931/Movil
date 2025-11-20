package com.example.appinterface.usuarios

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Cupones.CrearCuponActivity
import com.example.appinterface.Cupones.EditarCuponActivity
import com.example.appinterface.Cupones.ListaCuponesActivity
import com.example.appinterface.R

class CuponesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cupones)

        val btnConsultarCupones = findViewById<Button>(R.id.btnMostrar)

        btnConsultarCupones.setOnClickListener {
            val intent = Intent(this, ListaCuponesActivity::class.java)
            startActivity(intent)
        }

        val btnVolver = findViewById<ImageView>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            finish()
        }

        val btnAgregarCupon = findViewById<Button>(R.id.btnActualizar) // el botón "Agregar Cupones"
        btnAgregarCupon.setOnClickListener {
            Log.d("CUPONES_ACTIVITY", "btnAgregarCupon clickeado")
            Toast.makeText(this, "Ir a Agregar Cupón", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, CrearCuponActivity::class.java)
            startActivity(intent)
        }


    }
}
