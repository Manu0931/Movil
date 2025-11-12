package com.example.appinterface.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.R
import com.example.appinterface.Ventas.Envio

class EnvioAdapter(private val lista: List<Envio>) :
    RecyclerView.Adapter<EnvioAdapter.EnvioViewHolder>() {

    inner class EnvioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val direccion: TextView = view.findViewById(R.id.tvDireccionEnvio)
        val fecha: TextView = view.findViewById(R.id.tvFechaEnvio)
        val metodo: TextView = view.findViewById(R.id.tvMetodoEnvio)
        val estado: TextView = view.findViewById(R.id.tvEstadoEnvio)
        val icono: ImageView = view.findViewById(R.id.imgStatusEnvio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnvioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_envio, parent, false)
        return EnvioViewHolder(view)
    }

    override fun onBindViewHolder(holder: EnvioViewHolder, position: Int) {
        val envio = lista[position]

        holder.direccion.text = envio.direccionEnvio
        holder.fecha.text = "Fecha: ${envio.fechaEnvio}"
        holder.metodo.text = "Método: ${envio.metodoEnvio}"
        holder.estado.text = envio.estadoEnvio


        when (envio.estadoEnvio.lowercase()) {
            "pendiente" -> {
                holder.estado.setTextColor(0xFFFFC107.toInt()) // Amarillo
                holder.icono.setImageResource(R.drawable.ic_pending)
            }
            "enviado" -> {
                holder.estado.setTextColor(0xFF0D6EFD.toInt()) // Azul
                holder.icono.setImageResource(R.drawable.ic_shipping)
            }
            "entregado" -> {
                holder.estado.setTextColor(0xFF198754.toInt()) // Verde
                holder.icono.setImageResource(R.drawable.ic_delivered)
            }
            else -> {
                holder.estado.setTextColor(0xFF6C757D.toInt()) // Gris
                holder.icono.setImageResource(R.drawable.ic_unknown)
            }
        }
    }

    override fun getItemCount(): Int = lista.size
}
