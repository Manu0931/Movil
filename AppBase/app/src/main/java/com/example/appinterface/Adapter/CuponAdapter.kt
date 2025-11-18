package com.example.appinterface.Adapter.Cupon

import android.R.attr.id
import android.app.AlertDialog
import android.content.Intent
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Api.RetrofitInstance
import com.example.appinterface.Cupones.Cupon
import com.example.appinterface.Cupones.EditarCuponActivity
import com.example.appinterface.R
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
class CuponAdapter(
    private val context: Context,
    private val listaCupones: List<Cupon>, onEditar: Function<Unit>, onEliminar: Any
) :
    RecyclerView.Adapter<CuponAdapter.CuponViewHolder>() {

    inner class CuponViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val codigo: TextView = view.findViewById(R.id.tvCodigo)
        val descuento: TextView = view.findViewById(R.id.tvDescuento)
        val fecha: TextView = view.findViewById(R.id.tvFecha)
        val btnEditar: ImageView = view.findViewById(R.id.btnEditarCupon)
        val btnEliminar: ImageView = view.findViewById(R.id.btnEliminarCupon)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuponViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cupon, parent, false)
        return CuponViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuponViewHolder, position: Int) {
        val cupon = listaCupones[position]
        val pos = position

        holder.codigo.text = "Código: ${cupon.codigo}"
        holder.descuento.text = "Descuento: ${cupon.descuento}%"
        holder.fecha.text = "Expira: ${cupon.fecha_Expiracion}"

        // ------------------------
        //  BOTÓN EDITAR CUPÓN
        // ------------------------
        holder.btnEditar.setOnClickListener {
            val intent = Intent(context, EditarCuponActivity::class.java)
            intent.putExtra("id", cupon.ID_Cupon)
            intent.putExtra("codigo", cupon.codigo)
            intent.putExtra("descuento", cupon.descuento)
            intent.putExtra("fecha_Expiracion", cupon.fecha_Expiracion)
            context.startActivity(intent)
        }

        // ------------------------
        //  BOTÓN ELIMINAR CUPÓN
        // ------------------------
        holder.btnEliminar.setOnClickListener {

            AlertDialog.Builder(context)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Deseas eliminar el cupón con código ${cupon.codigo}?")
                .setPositiveButton("Sí") { dialog, _ ->

                    RetrofitInstance.empleadosApi.eliminarCupon(cupon.ID_Cupon!!)
                        .enqueue(object : Callback<Cupon> {
                            override fun onResponse(call: Call<Cupon>, response: Response<Cupon>) {
                                if (response.isSuccessful) {

                                    notifyItemRemoved(pos)
                                    notifyItemRangeChanged(pos, listaCupones.size)

                                    Toast.makeText(context, "Cupón eliminado", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<Cupon>, t: Throwable) {
                                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                            }
                        })

                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    override fun getItemCount(): Int = listaCupones.size
}