package com.example.digitalfactoring

import android.text.format.DateFormat

//data class Tarea(val nombre : String, val terminado : Boolean)
data class Tarea(
    val cliente : String,// top
    val numFactura : String,
    val monto : String, // esto es VNPP
    val estado : String, // top
    val fecha : String,
    val serie: String,
    val numero: String,
    val fechapago: String,
    val moneda: String)

//Agregar
// serie,numero,fechapago,moneda,precioventa