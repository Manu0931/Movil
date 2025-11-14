package com.example.appinterface

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Ventas.VentasActivity
import com.example.appinterface.databinding.ActivityAdminPanelBinding
import com.example.appinterface.producto.ProductosActivity
import com.example.appinterface.usuarios.EmpleadosActivity

class AdminPanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminPanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el nombre o correo del admin
        val adminName = intent.getStringExtra("nombre") ?: "Administrador"
        Toast.makeText(this, "Bienvenido $adminName", Toast.LENGTH_SHORT).show()

        // Listeners para cada card
        binding.cardClientes.setOnClickListener {
            startActivity(Intent(this, EmpleadosActivity::class.java))
        }

        binding.cardProductos.setOnClickListener {
            val intent = Intent(this, ProductosActivity::class.java)
            startActivity(intent)
            }

            binding.cardInventario.setOnClickListener {
            Toast.makeText(this, "Módulo de Inventario (en construcción)", Toast.LENGTH_SHORT).show()
        }

        binding.cardVentas.setOnClickListener {
            startActivity(Intent(this, VentasActivity::class.java))
        }
    }
}
