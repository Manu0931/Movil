package com.example.appinterface.Adapter.Cupon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Cupones.Cupon
import com.example.appinterface.R


class CuponAdapter(private val listaCupones: List<Cupon>) :
    RecyclerView.Adapter<CuponAdapter.CuponViewHolder>() {

    class CuponViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codigo: TextView = itemView.findViewById(R.id.tvCodigo)
        val descuento: TextView = itemView.findViewById(R.id.tvDescuento)
        val fecha: TextView = itemView.findViewById(R.id.tvFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuponViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cupon, parent, false)
        return CuponViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuponViewHolder, position: Int) {
        val cupon = listaCupones[position]
        holder.codigo.text = "Código: ${cupon.codigo}"
        holder.descuento.text = "Descuento: ${cupon.descuento}%"
        holder.fecha.text = "Expira: ${cupon.fecha_Expiracion}"
    }


    override fun getItemCount(): Int = listaCupones.size
}
