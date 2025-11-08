package com.example.appinterface.usuarios

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EmpleadoFormActivity : AppCompatActivity() {

    private lateinit var nombre: EditText
    private lateinit var cargo: EditText
    private lateinit var correo: EditText
    private lateinit var contrasena: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleado_form)

        nombre = findViewById(R.id.nombre)
        cargo = findViewById(R.id.cargo)
        correo = findViewById(R.id.correo)
        contrasena = findViewById(R.id.contrasena)
        val btnVolver = findViewById<ImageButton>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun crearEmpleado(v: View) {
        val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val empleado = Empleado(
            nombre = nombre.text.toString().trim(),
            cargo = cargo.text.toString().trim(),
            correo = correo.text.toString().trim(),
            contrasena = contrasena.text.toString().trim(),
            fechaContratacion = fechaActual,
            estado = "Activo"
        )

        if (empleado.nombre.isEmpty() || empleado.cargo.isEmpty() ||
            empleado.correo.isEmpty() || empleado.contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        RetrofitInstance.empleadosApi.crearEmpleado(empleado)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@EmpleadoFormActivity,
                            "Empleado creado correctamente",
                            Toast.LENGTH_LONG
                        ).show()
                        limpiarCampos()
                    } else {
                        Toast.makeText(
                            this@EmpleadoFormActivity,
                            "Error al crear empleado: ${response.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(
                        this@EmpleadoFormActivity,
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
    }
}

