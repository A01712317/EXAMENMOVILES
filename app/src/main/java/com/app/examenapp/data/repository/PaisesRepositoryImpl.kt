package com.app.examenapp.data.repository

import com.app.examenapp.data.local.preferences.PaisPreferences
import com.app.examenapp.data.mapper.toDomain
import com.app.examenapp.data.remote.api.PaisesApi
import com.app.examenapp.data.remote.dto.PaisDto
import com.app.examenapp.domain.model.Pais
import com.app.examenapp.domain.repository.PaisRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaisRepositoryImpl @Inject constructor(
    private val api: PaisesApi,
    private val preferences: PaisPreferences,
) : PaisRepository {

    override suspend fun getPaisesLista(): List<Pais> {
        // 1️ Intentar obtener desde cache
        preferences.getPaisCache()?.let { cache ->
            if (preferences.isCacheValid()) {
                return cache.paisList.map { it.toDomain() }
            }
        }

        // 2️ Si no hay cache o expiró, llamar al API
        return try {
            val response: List<PaisDto> = api.getPaisesLista()
            val paises = response.map { it.toDomain() }

            // 3️ Guardar en cache (guardas DTOs)
            preferences.savePaisesLista(
                paisList = response,
                offset = paises.size,
                totalCount = paises.size
            )

            paises
        } catch (e: Exception) {
            // 4️ Si falla, usar cache aunque esté vencido
            preferences.getPaisCache()?.let { cache ->
                cache.paisList.map { it.toDomain() }
            } ?: throw e
        }
    }

    override suspend fun getPais(nombre: String): Pais {
        // 1. Buscar primero en cache
        preferences.getPaisCache()?.let { cache ->
            if (preferences.isCacheValid()) {
                cache.paisList.find { it.name.common.equals(nombre, ignoreCase = true) }
                    ?.let { paisDto ->
                        // VALIDAR SI TIENE DATOS COMPLETOS
                        val tieneDetalles = paisDto.capital != null ||
                                paisDto.population != null ||
                                paisDto.area != null

                        if (tieneDetalles) {
                            return paisDto.toDomain()
                        }
                    }
            }
        }

        // 2. Si no está o está incompleto, llamar a la API
        return try {
            val response: List<PaisDto> = api.getPais(nombre)

            if (response.isEmpty()) {
                throw Exception("No se encontró el país")
            }

            val paisDto = response.first()

            // Actualizar/reemplazar en el caché con datos COMPLETOS
            preferences.getPaisCache()?.let { currentCache ->
                val updatedList = currentCache.paisList.toMutableList().apply {
                    // Remover el país incompleto si existe
                    removeAll { it.name.common.equals(paisDto.name.common, ignoreCase = true) }
                    // Agregar el país con datos completos
                    add(paisDto)
                }.toList()

                preferences.savePaisesLista(
                    paisList = updatedList,
                    offset = updatedList.size,
                    totalCount = updatedList.size
                )
            }

            paisDto.toDomain()
        } catch (e: Exception) {
            // Si falla, intentar fallback a caché (aunque esté vencido o incompleto)
            preferences.getPaisCache()?.let { cache ->
                cache.paisList.find { it.name.common.equals(nombre, ignoreCase = true) }
                    ?.toDomain()
            } ?: run {
                throw e
            }
        }
    }
}