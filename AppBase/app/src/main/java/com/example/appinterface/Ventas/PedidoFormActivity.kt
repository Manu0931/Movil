package com.example.appinterface.Ventas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
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

class PedidoFormActivity : AppCompatActivity() {

    private lateinit var idCliente: EditText
    private lateinit var estado: EditText
    private lateinit var total: EditText
    private lateinit var fechaPedido: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_form) // <- TU LAYOUT

        idCliente = findViewById(R.id.idClientePedido)
        estado = findViewById(R.id.estadoPedido)
        total = findViewById(R.id.totalPedido)
        fechaPedido = findViewById(R.id.fechaPedido)


        val fechaActual = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        fechaPedido.setText(fechaActual)

        val btnVolver = findViewById<ImageButton>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun crearPedido(v: View) {

        val pedido = Pedido(
            id_Pedido = null,
            id_Cliente = idCliente.text.toString().trim().toIntOrNull() ?: -1,
            fecha_Pedido = fechaPedido.text.toString().trim(),
            estado = estado.text.toString().trim(),
            total = total.text.toString().trim().toDoubleOrNull() ?: -1.0
        )

        if (pedido.id_Cliente == -1 ||
            pedido.fecha_Pedido.isNullOrEmpty() ||
            pedido.estado.isEmpty() ||
            pedido.total <= 0
        ) {
            Toast.makeText(this, "Llene todos los campos correctamente", Toast.LENGTH_SHORT).show()
            return
        }

        RetrofitInstance.pedidoApi.crearPedido(pedido)
            .enqueue(object : Callback<Pedido> {
                override fun onResponse(call: Call<Pedido>, data: Response<Pedido>) {
                    if (data.isSuccessful) {
                        Toast.makeText(
                            this@PedidoFormActivity,
                            "Pedido registrado correctamente",
                            Toast.LENGTH_LONG
                        ).show()


                        val intent = Intent(this@PedidoFormActivity, VentasActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(
                            this@PedidoFormActivity,
                            "Error al registrar el pedido: ${data.code()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Pedido>, t: Throwable) {
                    Log.e("ERROR_PEDIDO", "Fallo en la vuelta", t)
                    Toast.makeText(
                        this@PedidoFormActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun limpiarCampos() {
        idCliente.text.clear()
        estado.text.clear()
        total.text.clear()
    }
}
