package com.example.appinterface.producto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Producto(

    @SerializedName("id_Producto")
    val idProducto: Int,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("precio")
    val precio: Double,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("id_Proveedor")
    val idProveedor: Int,

    @SerializedName("imagen")
    val imagen: String?,

    @SerializedName("estado")
    val estado: String
) : Serializable
