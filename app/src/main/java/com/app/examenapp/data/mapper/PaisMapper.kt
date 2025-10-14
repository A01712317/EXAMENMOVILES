package com.app.examenapp.data.mapper

import com.app.examenapp.data.remote.dto.PaisDto
import com.app.examenapp.domain.model.Pais

fun PaisDto.toDomain(): Pais = Pais(
    nombre = name.common,
    nombreOficial = name.official,
    capital = capital?.firstOrNull(),
    region = region,
    subregion = subregion,
    poblacion = population,
    area = area,
    banderaUrl = flags?.png,
    escudoUrl = coatOfArms?.png,
    continente = continents?.firstOrNull(),
    idiomas = languages?.values?.toList(),
    monedas = currencies?.values?.map { "${it.name} (${it.symbol})" },
    gentilicio = demonyms?.get("eng")?.m,
    mapaUrl = maps?.googleMaps
)



