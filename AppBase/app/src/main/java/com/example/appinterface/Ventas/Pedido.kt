package com.example.appinterface.Ventas

import java.util.*


data class Pedido(
    val id_Pedido: Int,
    val id_Cliente: Int,
    val fecha_Pedido: String?,
    val estado: String,
    val total: Double
)
