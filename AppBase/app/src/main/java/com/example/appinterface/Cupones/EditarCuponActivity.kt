package com.example.appinterface.Cupones

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarCuponActivity : AppCompatActivity() {

    private lateinit var etCodigo: EditText
    private lateinit var etDescuento: EditText
    private lateinit var etFecha: EditText
    private lateinit var btnActualizar: Button

    private var idCupon: Int = 0  // ID recibido desde la lista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_cupones)

        etCodigo = findViewById(R.id.etCodigo)
        etDescuento = findViewById(R.id.etDescuento)
        etFecha = findViewById(R.id.etFecha)
        btnActualizar = findViewById(R.id.btnActualizar)

        idCupon = intent.getIntExtra("id", 0)
        val codigo = intent.getStringExtra("codigo") ?: ""
        val descuento = intent.getIntExtra("descuento", 0)
        val fecha = intent.getStringExtra("fecha_Expiracion") ?: ""


        etCodigo.setText(codigo.toString())
        etDescuento.setText(descuento.toString())
        etFecha.setText(fecha)

        btnActualizar.setOnClickListener {
            actualizarCupon()
        }
    }

    private fun actualizarCupon() {

        //Crear objeto cupon con los nuevos valores
        val cuponActualizado = Cupon(
            ID_Cupon = idCupon,
            codigo = etCodigo.text.toString(),
            descuento = etDescuento.text.toString().toInt(),
            fecha_Expiracion = etFecha.text.toString()
        )

        RetrofitInstance.empleadosApi.actualizarCupon(idCupon, cuponActualizado)
            .enqueue(object : Callback<Cupon> {
            override fun onResponse(call: Call<Cupon>, response: Response<Cupon>) {

                if (!response.isSuccessful) {

                    val codigo = response.code()
                    val errorBody = response.errorBody()?.string() ?: "Sin mensaje del servidor"

                    Toast.makeText(
                        this@EditarCuponActivity,
                        "Error $codigo -> $errorBody",
                        Toast.LENGTH_LONG
                    ).show()

                    return
                }


            }

            override fun onFailure(call: Call<Cupon>, t: Throwable) {
                Toast.makeText(
                    this@EditarCuponActivity,
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
