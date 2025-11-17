package com.example.appinterface.producto

import java.io.Serializable

data class Producto(
    val idProducto: Int? = null,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val idProveedor: Int,
    val imagen: String,
    val estado: String
) : Serializable
