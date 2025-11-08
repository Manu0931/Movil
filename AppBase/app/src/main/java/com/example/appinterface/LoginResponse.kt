package com.example.appinterface

data class LoginResponse(
    val success: Boolean,
    val mensaje: String?,
    val nombre: String?,
    val rol: String?
)
