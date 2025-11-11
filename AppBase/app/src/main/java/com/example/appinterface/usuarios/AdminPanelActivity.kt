package com.example.appinterface.usuarios

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.ProductosActivity
import com.example.appinterface.VentasActivity
import com.example.appinterface.databinding.ActivityAdminPanelBinding

class AdminPanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminPanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adminName = intent.getStringExtra("nombre") ?: "Administrador"
        Toast.makeText(this, "Bienvenido $adminName", Toast.LENGTH_SHORT).show()

        // Listeners para cada card
        binding.cardClientes.setOnClickListener {
            startActivity(Intent(this, UsuariosActivity::class.java))
        }

        binding.cardProductos.setOnClickListener {
            startActivity(Intent(this, ProductosActivity::class.java))
        }

        binding.cardInventario.setOnClickListener {
            Toast.makeText(this, "Módulo de Inventario (en construcción)", Toast.LENGTH_SHORT).show()
        }

        binding.cardVentas.setOnClickListener {
            startActivity(Intent(this, VentasActivity::class.java))
        }
    }
}
