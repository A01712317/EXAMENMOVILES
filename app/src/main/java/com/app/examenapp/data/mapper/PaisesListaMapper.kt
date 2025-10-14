package com.app.examenapp.data.mapper

import com.app.examenapp.data.remote.dto.PaisesListaDto
import com.app.examenapp.domain.model.Pais

fun PaisesListaDto.toDomain(): Pais = Pais(
    nombre = name.common,
    nombreOficial = name.official,
    capital = null,         // no viene en este DTO
    region = null,          // no viene en este DTO
    subregion = null,
    poblacion = null,
    area = null,
    banderaUrl = null,      // no viene en este DTO
    escudoUrl = null,
    continente = null,
    idiomas = null,
    monedas = null,
    gentilicio = null,
    mapaUrl = null
)
