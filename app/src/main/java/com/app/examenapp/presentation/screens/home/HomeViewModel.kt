package com.app.examenapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examenapp.domain.model.Pais
import com.app.examenapp.domain.usecase.GetPaisesListaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Clase que representa el estado de la UI
data class PaisesListaState(
    val paises: List<Pais> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PaisesListaViewModel @Inject constructor(
    private val getPaisesListaUseCase: GetPaisesListaUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PaisesListaState())
    val state: StateFlow<PaisesListaState> = _state

    init {
        obtenerPaises()
    }

    fun obtenerPaises() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val paises = getPaisesListaUseCase()
                _state.value = _state.value.copy(
                    paises = paises,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }
}
