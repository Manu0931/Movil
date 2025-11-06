package com.example.appinterface

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnUsuario: ImageView = findViewById(R.id.btnUsuario)

        btnUsuario.setOnClickListener {
            // Mostrar menú emergente al presionar el icono
            val popup = PopupMenu(this, btnUsuario)
            popup.menuInflater.inflate(R.menu.menu_usuario, popup.menu)

            // Acción al seleccionar un ítem
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_iniciar_sesion -> {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    fun abrirLogin(v: android.view.View) {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun abrirEmpleados(v: android.view.View) {
        startActivity(Intent(this, EmpleadosActivity::class.java))
    }

    fun abrirFormulario(v: android.view.View) {
        startActivity(Intent(this, EmpleadoFormActivity::class.java))
    }
}

