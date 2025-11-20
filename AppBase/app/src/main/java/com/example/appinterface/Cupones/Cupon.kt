package com.example.appinterface.Cupones

import com.google.gson.annotations.SerializedName


data class Cupon(
    @SerializedName("id_Cupon")
    val ID_Cupon: Int,
    val codigo: String,
    val descuento: Int,
    val fecha_Expiracion: String
)
