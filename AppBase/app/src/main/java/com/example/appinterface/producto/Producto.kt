package com.example.appinterface

data class Producto(
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val idProveedor: Int,
    val imagen: String,
    val estado: String
)
