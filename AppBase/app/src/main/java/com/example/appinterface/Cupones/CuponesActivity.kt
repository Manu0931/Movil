package com.example.appinterface.usuarios

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
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


    }
}
