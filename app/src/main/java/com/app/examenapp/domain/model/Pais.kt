package com.app.examenapp.domain.model

data class Pais(
    val nombre: String,
    val nombreOficial: String,
    val capital: String? = null,
    val region: String = "",
    val subregion: String = "",
    val poblacion: Long = 0L,
    val area: Double = 0.0,
    val banderaUrl: String? = null,
    val escudoUrl: String? = null,
    val continente: String = "",
    val idiomas: List<String> = emptyList(),
    val monedas: List<String> = emptyList(),
    val gentilicio: String = "",
    val mapaUrl: String? = null
)
