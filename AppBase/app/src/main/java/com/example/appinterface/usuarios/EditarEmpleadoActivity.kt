package com.example.appinterface.usuarios

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarEmpleadoActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCargo: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etContrasena: EditText
    private lateinit var etEstado: EditText
    private lateinit var btnActualizar: Button
    private lateinit var btnVolverEditar: ImageView

    private var empleadoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_empleado)

        etNombre = findViewById(R.id.etNombre)
        etCargo = findViewById(R.id.etCargo)
        etCorreo = findViewById(R.id.etCorreo)
        etContrasena = findViewById(R.id.etContrasena)
        etEstado = findViewById(R.id.etEstado)
        btnActualizar = findViewById(R.id.btnActualizarEmpleado)

        empleadoId = intent.getIntExtra("idEmpleado", 0)
        etNombre.setText(intent.getStringExtra("nombre"))
        etCargo.setText(intent.getStringExtra("cargo"))
        etCorreo.setText(intent.getStringExtra("correo"))
        etContrasena.setText(intent.getStringExtra("contrasena"))

        // Convertimos el estado de Int a String para mostrar
        val estadoInt = intent.getIntExtra("estado", 1)
        etEstado.setText(if (estadoInt == 1) "Activo" else "Inactivo")

        btnVolverEditar = findViewById(R.id.btnVolverEditar)
        btnVolverEditar.setOnClickListener { finish() }

        btnActualizar.setOnClickListener { actualizarEmpleado() }
    }

    private fun actualizarEmpleado() {
        val estadoTexto = if (etEstado.text.toString().trim().equals("Activo", ignoreCase = true)) {
            "Activo"
        } else {
            "Inactivo"
        }

        val empleadoActualizado = Empleado(
            idEmpleado = empleadoId,
            nombre = etNombre.text.toString(),
            cargo = etCargo.text.toString(),
            correo = etCorreo.text.toString(),
            contrasena = etContrasena.text.toString(),
            estado = estadoTexto
        )

        RetrofitInstance.empleadosApi.actualizarEmpleado(empleadoId, empleadoActualizado)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@EditarEmpleadoActivity, "Empleado actualizado", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@EditarEmpleadoActivity, "Error al actualizar", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Toast.makeText(this@EditarEmpleadoActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
