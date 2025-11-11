package com.example.appinterface.modelos

data class RegistroRequest(
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val documento: String,
    val telefono: String
)
