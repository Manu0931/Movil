package com.example.appinterface.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Api.Empleado
import com.example.appinterface.R

class EmpleadoAdapter(private val lista: List<Empleado>) :
    RecyclerView.Adapter<EmpleadoAdapter.EmpleadoViewHolder>() {

    inner class EmpleadoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombre)
        val cargo: TextView = view.findViewById(R.id.tvCargo)
        val estado: TextView = view.findViewById(R.id.tvEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpleadoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_empleado, parent, false)
        return EmpleadoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmpleadoViewHolder, position: Int) {
        val empleado = lista[position]
        holder.nombre.text = empleado.nombre
        holder.cargo.text = "Cargo: ${empleado.cargo}"
        holder.estado.text = "Estado: ${empleado.estado}"

        holder.estado.setTextColor(
            if (empleado.estado.lowercase() == "activo") 0xFF198754.toInt() else 0xFFDC3545.toInt()
        )
    }

    override fun getItemCount(): Int = lista.size
}
