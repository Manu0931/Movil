package com.example.appinterface.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.R
import com.example.appinterface.producto.EditarProductoActivity
import com.example.appinterface.producto.Producto
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoAdapter(
    private val lista: MutableList<Producto>,
    private val context: Context
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombre)
        val descripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val precio: TextView = view.findViewById(R.id.tvPrecio)
        val estado : TextView = view.findViewById(R.id.tvEstado)
        val imagen: ImageView = view.findViewById(R.id.imgProducto)
        val btnEditar: ImageView = view.findViewById(R.id.btnEditarProducto)
        val btnEliminarProducto: ImageView = view.findViewById(R.id.btnEliminarProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = lista[position]
        val pos = position

        holder.nombre.text = producto.nombre
        holder.descripcion.text = producto.descripcion
        holder.precio.text = "$${producto.precio}"
        holder.estado.text = producto.estado

        val urlImagen = "http://10.0.2.2:8080/uploads/productos/${producto.imagen}"

        Picasso.get()
            .load(urlImagen)
            .into(holder.imagen)


        // Editar producto
        holder.btnEditar.setOnClickListener {
            Log.d("ADAPTER", "ID que se envía → ${producto.idProducto}")

            val intent = Intent(context, EditarProductoActivity::class.java)

            // Pasar datos del producto
            intent.putExtra("idProducto", producto.idProducto)
            intent.putExtra("nombre", producto.nombre)
            intent.putExtra("descripcion", producto.descripcion)
            intent.putExtra("precio", producto.precio)
            intent.putExtra("stock", producto.stock)
            intent.putExtra("id_Proveedor", producto.idProveedor)
            intent.putExtra("estado", producto.estado)
            intent.putExtra("imagen", producto.imagen)

            context.startActivity(intent)
        }

        // Eliminar producto
        holder.btnEliminarProducto.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Deseas eliminar el producto '${producto.nombre}'?")
                .setPositiveButton("Sí") { dialog, _ ->

                    producto.idProducto?.let { id ->

                        RetrofitInstance.productosApi.eliminarProducto(id)
                            .enqueue(object : Callback<Void> {
                                override fun onResponse(
                                    call: Call<Void>,
                                    response: Response<Void>
                                ) {
                                    if (response.isSuccessful) {

                                        lista.removeAt(pos)
                                        notifyItemRemoved(pos)
                                        notifyItemRangeChanged(pos, lista.size)

                                        Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                                }
                            })

                    } ?: run {
                        // Si idProducto es null
                        Toast.makeText(context, "ID de producto inválido", Toast.LENGTH_SHORT).show()
                    }

                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                .show()
        }

    }

    override fun getItemCount(): Int = lista.size
}
