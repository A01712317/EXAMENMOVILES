package com.app.examenapp.data.local.model

import com.app.examenapp.data.remote.dto.PaisDto


data class PaisCache(
    val paisList: List<PaisDto>,
    val lastUpdate: Long,
    val offset: Int,
    val totalCount: Int,
)