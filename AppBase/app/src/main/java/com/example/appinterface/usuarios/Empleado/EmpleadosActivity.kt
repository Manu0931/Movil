package com.example.appinterface.usuarios.Empleado

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Adapter.EmpleadoAdapter
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmpleadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empleados)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerEmpleados)
        recyclerView.layoutManager = LinearLayoutManager(this)

        findViewById<ImageView>(R.id.btnVolverEmpleados).setOnClickListener {
            finish()
        }


        RetrofitInstance.empleadosApi.getEmpleados().enqueue(object : Callback<List<Empleado>> {
            override fun onResponse(call: Call<List<Empleado>>, response: Response<List<Empleado>>) {
                val empleados = response.body()
                if (response.isSuccessful && !empleados.isNullOrEmpty()) {
                    recyclerView.adapter = EmpleadoAdapter(empleados.toMutableList(), this@EmpleadosActivity)
                } else {
                    Toast.makeText(this@EmpleadosActivity, "No hay empleados disponibles", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Empleado>>, t: Throwable) {
                Toast.makeText(this@EmpleadosActivity, "Error de conexión: ${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })

    }
}