package com.example.appinterface.usuarios

import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.R
import com.example.appinterface.databinding.ActivityPanelClienteBinding
import com.example.appinterface.logueo.LoginActivity

class PanelCliente : AppCompatActivity() {

    private lateinit var binding: ActivityPanelClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPanelClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombreCliente = intent.getStringExtra("nombreCliente") ?: "Cliente"
        binding.tvBienvenida.text = "Bienvenido, $nombreCliente 👋"

        binding.btnProductos.setOnClickListener {
            startActivity(Intent(this, ("ProductosActivity")::class.java))
        }

        binding.btnCarrito.setOnClickListener {
            startActivity(Intent(this, ("CarritoActivity")::class.java))
        }

        binding.btnPerfil.setOnClickListener {
            val popup = PopupMenu(this, binding.btnPerfil)
            popup.menuInflater.inflate(R.menu.menu_cliente, popup.menu)

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_perfil -> {
                        startActivity(Intent(this, ("PerfilActivity")::class.java))
                        true
                    }
                    R.id.menu_configuracion -> true
                    R.id.menu_cerrar_sesion -> {
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        binding.cardPedidos.setOnClickListener {}
        binding.cardRecomendados.setOnClickListener {}
        binding.cardOfertas.setOnClickListener {}
        binding.cardSoporte.setOnClickListener {}
        binding.cardContacto.setOnClickListener {}
    }
}
