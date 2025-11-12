package com.example.appinterface.usuarios

data class cliente(
    val ID_Cliente: Int? = null,
    val Nombre: String,
    val Correo: String,
    val Contrasena: String,
    val Fecha_Registro: String,
    val Estado: String = "Activo",
    val Documento: String,
    val Telefono: String
)
