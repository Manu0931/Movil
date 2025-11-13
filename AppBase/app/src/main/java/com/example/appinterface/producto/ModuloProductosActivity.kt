package com.example.appinterface.producto

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.R
import com.example.appinterface.databinding.ActivityModuloProductosBinding
import com.example.appinterface.logueo.LoginActivity

class ModuloProductosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityModuloProductosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModuloProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Módulo de Productos", Toast.LENGTH_SHORT).show()


        // Botón para consultar productos
        binding.btnConsultarProductos.setOnClickListener {
            val intent = Intent(this, ProductosActivity::class.java)
            startActivity(intent)
        }

        // Botón para agregar nuevo producto
        binding.btnAgregarProductos.setOnClickListener {
            val intent = Intent(this, ProductosActivity::class.java)
            startActivity(intent)
        }
    }
}
