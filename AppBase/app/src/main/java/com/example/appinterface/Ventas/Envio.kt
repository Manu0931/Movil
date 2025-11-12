package com.example.appinterface.Ventas

data class Envio(
    val ID_Envio: Int,
    val ID_Pedido: Int,
    val direccionEnvio: String,
    val fechaEnvio: String,
    val metodoEnvio: String,
    val estadoEnvio: String
)