package com.app.examenapp.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.examenapp.domain.model.Pais
import com.app.examenapp.presentation.screens.home.components.PaisItem

@Composable
fun HomeScreen(
    viewModel: PaisesListaViewModel = hiltViewModel(),
    onPaisClick: (Pais) -> Unit
) {
    val state by viewModel.state.collectAsState()

    // Transformamos a HomeUiState
    val uiState = HomeUiState(
        isLoading = state.isLoading,
        error = state.error
    )

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            uiState.error != null -> {
                Text(
                    text = "Error: ${uiState.error}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.paises) { pais ->
                        PaisItem(pais = pais, onClick = { onPaisClick(pais) })
                    }
                }
            }
        }
    }
}
