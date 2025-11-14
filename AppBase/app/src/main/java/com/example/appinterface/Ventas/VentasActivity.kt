package com.example.appinterface.Ventas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.R

class VentasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ventas)

        val btnConsultarEnvios = findViewById<Button>(R.id.btnConsultarEnvios)
        val btnConsultarPedido = findViewById<Button>(R.id.btnConsultarPedidos)


        btnConsultarEnvios.setOnClickListener {
            val intent = Intent(this, EnviosActivity::class.java)
            startActivity(intent)
        }


        btnConsultarPedido.setOnClickListener {
            val intent = Intent(this, PedidoActivity::class.java)
            startActivity(intent)
        }

    }
}
