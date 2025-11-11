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
import com.example.appinterface.usuarios.cliente
import com.example.appinterface.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClienteAdapter(
    private val lista: MutableList<cliente>,
    private val context: Context
) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    inner class ClienteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombreCliente)
        val correo: TextView = view.findViewById(R.id.tvCorreoCliente)
        val estado: TextView = view.findViewById(R.id.tvEstadoCliente)
        val btnEditar: ImageView = view.findViewById(R.id.btnEditarCliente)
        val btnEliminar: ImageView = view.findViewById(R.id.btnEliminarCliente)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val clienteActual = lista[position]
        val pos = position

        holder.nombre.text = clienteActual.Nombre
        holder.correo.text = clienteActual.Correo
        holder.estado.text = "Estado: ${clienteActual.Estado}"

        holder.estado.setTextColor(
            if (clienteActual.Estado.equals("Activo", ignoreCase = true)) 0xFF198754.toInt()
            else 0xFFD32F2F.toInt()
        )

        // Editar cliente
        holder.btnEditar.setOnClickListener {
            val intent = Intent(context, ("ClienteEditarActivity")::class.java)
            intent.putExtra("idCliente", clienteActual.ID_Cliente)
            context.startActivity(intent)
        }

        // Eliminar cliente
        holder.btnEliminar.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Deseas eliminar a ${clienteActual.Nombre}?")
                .setPositiveButton("Sí") { dialog, _ ->
                    RetrofitInstance.empleadosApi.eliminarClientes(clienteActual.ID_Cliente!!)
                        .enqueue(object : Callback<String> {
                            override fun onResponse(call: Call<String>, response: Response<String>) {
                                if (response.isSuccessful) {
                                    lista.removeAt(pos)
                                    notifyItemRemoved(pos)
                                    notifyItemRangeChanged(pos, lista.size)
                                    Toast.makeText(context, "Cliente eliminado", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
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
