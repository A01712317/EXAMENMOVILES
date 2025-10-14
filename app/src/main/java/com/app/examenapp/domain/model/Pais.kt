package com.app.examenapp.domain.model

data class Pais(
    val nombre: String,
    val nombreOficial: String,
    val capital: String?,
    val region: String?,
    val subregion: String?,
    val poblacion: Long?,
    val area: Double?,
    val banderaUrl: String?,
    val escudoUrl: String?,
    val continente: String?,
    val idiomas: List<String>?,
    val monedas: List<String>?,
    val gentilicio: String?,
    val mapaUrl: String?
)
