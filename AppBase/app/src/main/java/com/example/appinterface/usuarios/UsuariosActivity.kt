package com.example.appinterface.usuarios

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.R
import com.example.appinterface.producto.ProductosActivity
import com.example.appinterface.databinding.ActivityUsuariosBinding
import com.example.appinterface.logueo.LoginActivity
import com.example.appinterface.usuarios.Cliente.ClientesActivity
import com.example.appinterface.usuarios.Cliente.ClientesFormActivity
import com.example.appinterface.usuarios.Empleado.EmpleadoFormActivity
import com.example.appinterface.usuarios.Empleado.EmpleadosActivity

class UsuariosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsuariosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Módulo de Usuarios", Toast.LENGTH_SHORT).show()

        binding.btnUsuarios.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.menu_modulo_usuario, popup.menu)

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
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

                    R.id.menu_volver -> {
                        finish()
                        true
                    }

                    else -> false
                }
            }

            popup.show()
        }

        binding.btnConsultarClientes.setOnClickListener {
            startActivity(Intent(this, ClientesActivity::class.java))
        }

        binding.btnAgregarClientes.setOnClickListener {
            startActivity(Intent(this, ClientesFormActivity::class.java))
        }

        binding.btnConsultarEmpleados.setOnClickListener {
            startActivity(Intent(this, EmpleadosActivity::class.java))
        }

        binding.btnAgregarEmpleados.setOnClickListener {
            startActivity(Intent(this, EmpleadoFormActivity::class.java))
        }

    }
}
