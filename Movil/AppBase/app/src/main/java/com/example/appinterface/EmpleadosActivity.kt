package com.example.appinterface

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Adapter.EmpleadoAdapter
import com.example.appinterface.Api.Empleado
import com.example.appinterface.Api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmpleadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleados)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerEmpleados)
        recyclerView.layoutManager = LinearLayoutManager(this)


        RetrofitInstance.api2kotlin.getEmpleados().enqueue(object : Callback<List<Empleado>> {
            override fun onResponse(call: Call<List<Empleado>>, response: Response<List<Empleado>>) {
                if (response.isSuccessful) {
                    val empleados = response.body()
                    if (!empleados.isNullOrEmpty()) {
                        recyclerView.adapter = EmpleadoAdapter(empleados)
                    } else {
                        Toast.makeText(this@EmpleadosActivity, "No hay empleados disponibles", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@EmpleadosActivity, "Error HTTP ${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Respuesta no exitosa: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Empleado>>, t: Throwable) {
                Toast.makeText(this@EmpleadosActivity, "Error de conexión: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
                Log.e("API_ERROR", "Fallo al conectar con API", t)
            }
        })
    }

    fun volverpag(v: View) {
        Toast.makeText(this, "Volviendo atrás...", Toast.LENGTH_SHORT).show()
        onBackPressedDispatcher.onBackPressed()
    }
}
