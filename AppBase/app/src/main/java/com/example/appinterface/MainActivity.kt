package com.example.appinterface

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

