// package com.app.examenapp.data.mapper

import com.app.examenapp.data.remote.dto.PaisDto
import com.app.examenapp.domain.model.Pais

fun PaisDto.toDomain(): Pais = Pais(
    nombre = name.common,
    nombreOficial = name.official,
    capital = capital?.firstOrNull(),
    region = region ?: "",
    subregion = subregion ?: "",
    poblacion = population ?: 0L,
    area = area ?: 0.0,
    banderaUrl = flags?.png,
    escudoUrl = coatOfArms?.png,
    continente = continents?.firstOrNull() ?: "",
    idiomas = languages?.values?.toList() ?: emptyList(),
    monedas = currencies?.values
        ?.map { currency ->
            listOfNotNull(currency.name, currency.symbol)
                .joinToString(" (", postfix = ")") { it }
        }
        ?: emptyList(),
    // CORRECCIÓN: Cambiar 'm' por 'male'
    gentilicio = demonyms?.get("eng")?.male ?: "",
    mapaUrl = maps?.googleMaps
)