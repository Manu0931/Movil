package com.example.appinterface.Ventas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.R

class PedidoAdapter(private val listaPedidos: List<Pedido>) :
    RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = listaPedidos[position]
        holder.idPedido.text = "Pedido #${pedido.id_Pedido}"
        holder.idCliente.text = "Cliente: ${pedido.id_Cliente}"
        holder.fecha.text = "Fecha: ${pedido.fecha_Pedido}"
        holder.estado.text = "Estado: ${pedido.estado}"
        holder.total.text = "Total: $${pedido.total}"

        when (pedido.estado.lowercase()) {
            "pendiente" -> {
                holder.estado.setTextColor(0xFFFFC107.toInt()) // Amarillo
                holder.icono.setImageResource(R.drawable.ic_pending)
            }
            "enviado" -> {
                holder.estado.setTextColor(0xFF0D6EFD.toInt()) // Azul
                holder.icono.setImageResource(R.drawable.ic_shipping)
            }
            else -> {
                holder.estado.setTextColor(0xFF6C757D.toInt()) // Gris
                holder.icono.setImageResource(R.drawable.ic_unknown)
            }
        }
    }



    override fun getItemCount(): Int = listaPedidos.size

    class PedidoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idPedido: TextView = view.findViewById(R.id.tvIdPedido)
        val idCliente: TextView = view.findViewById(R.id.tvIdCliente)
        val fecha: TextView = view.findViewById(R.id.tvFechaPedido)
        val estado: TextView = view.findViewById(R.id.tvEstadoPedido)
        val total: TextView = view.findViewById(R.id.tvTotalPedido)
        val icono: ImageView = view.findViewById(R.id.imgStatusPedido)
    }


}
