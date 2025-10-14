package com.app.examenapp.domain.usecase

import com.app.examenapp.domain.model.Pais
import com.app.examenapp.domain.repository.PaisRepository
import jakarta.inject.Inject

class GetPaisDetalleUseCase @Inject constructor(
    private val repository: PaisRepository
) {
    suspend operator fun invoke(nombre: String): Pais {
        return repository.getPais(nombre)
    }
}