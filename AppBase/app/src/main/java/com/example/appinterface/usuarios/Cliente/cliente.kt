package com.example.appinterface.usuarios.Cliente

data class cliente(
    val idCliente: Int? = null,
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val fechaRegistro: String,
    val estado: String,
    val documento: String,
    val telefono: String
)