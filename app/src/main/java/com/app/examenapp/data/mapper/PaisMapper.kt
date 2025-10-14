package com.app.examenapp.data.mapper

import com.app.examenapp.data.remote.dto.PaisDto
import com.app.examenapp.data.remote.dto.PaisesListaDto
import com.app.examenapp.domain.model.Pais

// Mapper para PaisDto (respuesta completa de la API)
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
                .joinToString(" ")
        }
        ?: emptyList(),
    gentilicio = demonyms?.get("eng")?.male ?: "",
    mapaUrl = maps?.googleMaps
)

// Mapper para PaisesListaDto (respuesta de lista simplificada)
