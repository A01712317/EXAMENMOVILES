package com.app.examenapp.domain.repository

import com.app.examenapp.domain.model.Pais

interface PaisRepository {
    suspend fun getPaisesLista(): List<Pais>
    suspend fun getPais(nombre: String): Pais
}


