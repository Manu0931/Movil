package com.example.appinterface.usuarios

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.logueo.LoginActivity
import com.example.appinterface.R
import com.example.appinterface.databinding.ActivityAdminPanelBinding
import com.example.appinterface.producto.ModuloProductosActivity

class AdminPanelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminPanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adminName = intent.getStringExtra("nombre") ?: "Administrador"
        Toast.makeText(this, "Bienvenido $adminName", Toast.LENGTH_SHORT).show()


        binding.btnUsuario.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.menu_admin, popup.menu)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popup.setForceShowIcon(true)
            }

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_cerrar_sesion -> {
                        val sharedPref = getSharedPreferences("SesionUsuario", MODE_PRIVATE)
                        sharedPref.edit().clear().apply()

                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                        true
                    }

                    R.id.menu_configuracion -> {
                        Toast.makeText(this, "Configuración (en desarrollo)", Toast.LENGTH_SHORT).show()
                        true
                    }

                    else -> false
                }
            }

            popup.show()
        }


        binding.cardClientes.setOnClickListener {
            startActivity(Intent(this, UsuariosActivity::class.java))
        }

        binding.cardProductos.setOnClickListener {
            startActivity(Intent(this, ModuloProductosActivity::class.java))
        }

        binding.cardInventario.setOnClickListener {
            Toast.makeText(this, "Módulo de Inventario (en construcción)", Toast.LENGTH_SHORT).show()
        }

        binding.cardInventario.setOnClickListener {
            val intent = Intent(this, InventarioActivity::class.java)
            startActivity(intent)
        binding.cardVentas.setOnClickListener {
            Toast.makeText(this, "Módulo de Ventas (en construcción)", Toast.LENGTH_SHORT).show()
        }
    }
}}