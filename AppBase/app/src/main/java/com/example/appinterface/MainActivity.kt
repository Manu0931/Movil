package com.example.appinterface

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.Empleado
import com.example.appinterface.Api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var cargo: EditText
    private lateinit var correo: EditText
    private lateinit var contrasena: EditText
    private lateinit var estado: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nombre = findViewById(R.id.nombre)
        cargo = findViewById(R.id.cargo)
        correo = findViewById(R.id.correo)
        contrasena = findViewById(R.id.contrasena)
        estado = findViewById(R.id.estado)
    }

    fun crearEmpleado(v: View) {
        val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val nombreTxt = nombre.text.toString().trim()
        val cargoTxt = cargo.text.toString().trim()
        val correoTxt = correo.text.toString().trim()
        val contrasenaTxt = contrasena.text.toString().trim()
        val estadoTxt = estado.text.toString().trim().ifEmpty { "Activo" }

        if (nombreTxt.isEmpty() || cargoTxt.isEmpty() || correoTxt.isEmpty() || contrasenaTxt.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val empleado = Empleado(
            nombre = nombreTxt,
            cargo = cargoTxt,
            correo = correoTxt,
            contrasena = contrasenaTxt,
            fechaContratacion = fechaActual,
            estado = estadoTxt
        )

        RetrofitInstance.api2kotlin.crearEmpleado(empleado)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@MainActivity,
                            "Empleado creado correctamente",
                            Toast.LENGTH_LONG
                        ).show()
                        limpiarCampos()
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Error al crear empleado: ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Fallo de conexión: ${t.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun limpiarCampos() {
        nombre.text.clear()
        cargo.text.clear()
        correo.text.clear()
        contrasena.text.clear()
        estado.text.clear()
    }

    fun abrirEmpleados(v: View) {
        val intent = Intent(this, EmpleadosActivity::class.java)
        startActivity(intent)
    }
}
