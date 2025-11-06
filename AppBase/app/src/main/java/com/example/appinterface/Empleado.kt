package com.example.appinterface

data class Empleado(
    val nombre: String,
    val cargo: String,
    val correo: String,
    val contrasena: String,
    val fechaContratacion: String? = null,
    val estado: String? = "Activo"
)
