package com.example.appinterface.logueo

data class LoginResponse(
    val success: Boolean,
    val mensaje: String?,
    val nombre: String?,
    val rol: String?
)
