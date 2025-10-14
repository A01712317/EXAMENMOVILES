package com.app.examenapp.data.mapper

import com.app.examenapp.data.remote.dto.PaisesListaDto
import com.app.examenapp.domain.model.Pais

fun PaisesListaDto.toDomain(): Pais = Pais(
    nombre = name.common,
    nombreOficial = name.official,
    capital = null,
    region = "",           // String vacío en lugar de null
    subregion = "",        // String vacío en lugar de null
    poblacion = 0L,        // 0 en lugar de null
    area = 0.0,            // 0.0 en lugar de null
    banderaUrl = null,
    escudoUrl = null,
    continente = "",       // String vacío en lugar de null
    idiomas = emptyList(), // Lista vacía en lugar de null
    monedas = emptyList(), // Lista vacía en lugar de null
    gentilicio = "",       // String vacío en lugar de null
    mapaUrl = null
)
