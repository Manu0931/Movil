package com.example.appinterface

data class Cupon(
    var idCupon: Int = 0,
    var codigo: String = "",
    var descuento: Double = 0.0,
    var fechaExpiracion: String = "" // Ejemplo: "2025-10-29"
)
