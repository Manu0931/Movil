package com.example.appinterface.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.R
import com.example.appinterface.producto.Producto
import com.squareup.picasso.Picasso

class ProductoAdapter(
    private val productos: List<Producto>,
    private val onEditar: (Producto) -> Unit,
    private val onEliminar: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>(){

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombre)
        val descripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val precio: TextView = view.findViewById(R.id.tvPrecio)
        val imagen: ImageView = view.findViewById(R.id.imgProducto)
        val btnEditarProducto: ImageView = view.findViewById(R.id.btnEditarProducto)
        val btnEliminarProducto: ImageView = view.findViewById(R.id.btnEliminarProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.nombre.text = producto.nombre
        holder.descripcion.text = producto.descripcion
        holder.precio.text = "$${producto.precio}"

        Picasso.get().load(producto.imagen).into(holder.imagen)

        // Clic en editar
        holder.btnEditarProducto.setOnClickListener {
            onEditar(producto)
        }

        // Clic en eliminar
        holder.btnEliminarProducto.setOnClickListener {
            onEliminar(producto)
        }
    }

    override fun getItemCount() = productos.size
}
