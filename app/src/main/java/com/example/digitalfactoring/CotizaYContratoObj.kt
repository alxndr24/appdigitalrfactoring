package com.example.digitalfactoring

import android.text.format.DateFormat

//data class Tarea(val nombre : String, val terminado : Boolean)
data class CotizaYContratoObj(
    val cliente : String,// top
    val estado : String, // top
    val moneda : String,
    val adelanto : String,
    val tasa : String, // esto es VNPP
    val fechaDesembolso : String, // top
    val fechaVigencia : String,
    val desembolsoTotal: String,
    val remanenteTotal: String,
    val interes: String)

//Agregar
// serie,numero,fechapago,moneda,precioventa