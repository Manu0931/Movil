package com.example.appinterface.usuarios.Cliente

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

class EditarClientesActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etContrasena: EditText
    private lateinit var etEstado: EditText
    private lateinit var etDocumento: EditText
    private lateinit var etTelefono: EditText
    private lateinit var btnActualizarCliente: Button
    private lateinit var btnVolver: ImageView

    private var clienteId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cliente)

        etNombre = findViewById(R.id.etNombreCliente)
        etCorreo = findViewById(R.id.etCorreoCliente)
        etContrasena = findViewById(R.id.etContrasenaCliente)
        etEstado = findViewById(R.id.etEstadoCliente)
        etDocumento = findViewById(R.id.etDocumentoCliente)
        etTelefono = findViewById(R.id.etTelefonoCliente)
        btnActualizarCliente = findViewById(R.id.btnActualizarCliente)
        btnVolver = findViewById(R.id.btnVolverCliente)


        clienteId = intent.getIntExtra("idCliente", 0)
        etNombre.setText(intent.getStringExtra("nombre"))
        etCorreo.setText(intent.getStringExtra("correo"))
        etContrasena.setText(intent.getStringExtra("contrasena"))
        etEstado.setText(intent.getStringExtra("estado"))
        etDocumento.setText(intent.getStringExtra("documento"))
        etTelefono.setText(intent.getStringExtra("telefono"))

        btnVolver.setOnClickListener { finish() }
        btnActualizarCliente.setOnClickListener { actualizarCliente() }
    }

    private fun actualizarCliente() {
        val clienteActualizado = cliente(
            idCliente = clienteId,
            nombre = etNombre.text.toString(),
            correo = etCorreo.text.toString(),
            contrasena = etContrasena.text.toString(),
            fechaRegistro = "",
            estado = etEstado.text.toString(),
            documento = etDocumento.text.toString(),
            telefono = etTelefono.text.toString()
        )

        RetrofitInstance.empleadosApi.actualizarClientes(clienteId, clienteActualizado)
            .enqueue(object : Callback<cliente> {
                override fun onResponse(call: Call<cliente>, response: Response<cliente>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@EditarClientesActivity,
                            "Cliente actualizado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@EditarClientesActivity,
                            "Error al actualizar",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<cliente>, t: Throwable) {
                    Toast.makeText(
                        this@EditarClientesActivity,
                        "Error: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}