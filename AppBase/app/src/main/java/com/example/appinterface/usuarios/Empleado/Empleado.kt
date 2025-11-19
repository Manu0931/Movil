package com.example.appinterface.usuarios.Empleado

data class Empleado(
    val idEmpleado: Int? = null,
    val nombre: String,
    val cargo: String,
    val correo: String,
    val contrasena: String,
    val fechaContratacion: String? = null,
    val estado: String? = "Activo"
)