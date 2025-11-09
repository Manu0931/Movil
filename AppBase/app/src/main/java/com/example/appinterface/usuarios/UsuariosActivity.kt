package com.example.appinterface.usuarios

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.ProductosActivity
import com.example.appinterface.databinding.ActivityUsuariosBinding

class UsuariosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsuariosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Módulo de Usuarios", Toast.LENGTH_SHORT).show()

        binding.btnConsultarClientes.setOnClickListener {
            Toast.makeText(this, "Consultar Clientes", Toast.LENGTH_SHORT).show()
        }

        binding.btnAgregarClientes.setOnClickListener {
            Toast.makeText(this, "Agregar Cliente", Toast.LENGTH_SHORT).show()
        }

        binding.btnConsultarEmpleados.setOnClickListener {
            startActivity(Intent(this, EmpleadosActivity::class.java))
        }

        binding.btnAgregarEmpleados.setOnClickListener {
            startActivity(Intent(this, EmpleadoFormActivity::class.java))
        }

    }
}
