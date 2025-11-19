package com.example.appinterface.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.usuarios.Empleado.Empleado
import com.example.appinterface.R
import com.example.appinterface.usuarios.Empleado.EditarEmpleadoActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmpleadoAdapter(
    private val lista: MutableList<Empleado>,
    private val context: Context
) : RecyclerView.Adapter<EmpleadoAdapter.EmpleadoViewHolder>() {

    inner class EmpleadoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombre)
        val cargo: TextView = view.findViewById(R.id.tvCargo)
        val estado: TextView = view.findViewById(R.id.tvEstado)
        val btnEditar: ImageView = view.findViewById(R.id.btnEditarEmpleado)
        val btnEliminar: ImageView = view.findViewById(R.id.btnEliminarEmpleado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpleadoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_empleado, parent, false)
        return EmpleadoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmpleadoViewHolder, position: Int) {
        val empleado = lista[position]
        val pos = position

        holder.nombre.text = empleado.nombre
        holder.cargo.text = "Cargo: ${empleado.cargo}"
        holder.estado.text = "Estado: ${empleado.estado}"

        val estadoTexto = empleado.estado ?: "Inactivo"
        holder.estado.text = "Estado: $estadoTexto"
        holder.estado.setTextColor(
            if (estadoTexto.equals("Activo", ignoreCase = true)) 0xFF198754.toInt() else 0xFFD32F2F.toInt()
        )

        // Editar empleado
        holder.btnEditar.setOnClickListener {
            val intent = Intent(context, EditarEmpleadoActivity::class.java)
            intent.putExtra("idEmpleado", empleado.idEmpleado)
            intent.putExtra("nombre", empleado.nombre)
            intent.putExtra("cargo", empleado.cargo)
            intent.putExtra("correo", empleado.correo)
            intent.putExtra("contrasena", empleado.contrasena)
            intent.putExtra("estado", empleado.estado)
            context.startActivity(intent)
        }


        // Eliminar empleado con confirmación y llamada API
        holder.btnEliminar.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Deseas eliminar a ${empleado.nombre}?")
                .setPositiveButton("Sí") { dialog, _ ->
                    RetrofitInstance.empleadosApi.eliminarEmpleado(empleado.idEmpleado!!)
                        .enqueue(object : Callback<Empleado> {
                            override fun onResponse(call: Call<Empleado>, response: Response<Empleado>) {
                                if (response.isSuccessful) {
                                    lista.removeAt(pos)
                                    notifyItemRemoved(pos)
                                    notifyItemRangeChanged(pos, lista.size)
                                    Toast.makeText(context, "Empleado eliminado", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<Empleado>, t: Throwable) {
                                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    override fun getItemCount(): Int = lista.size
}
