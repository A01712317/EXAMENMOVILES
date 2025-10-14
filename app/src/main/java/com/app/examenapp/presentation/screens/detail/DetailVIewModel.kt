package com.app.examenapp.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.examenapp.domain.model.Pais
import com.app.examenapp.domain.repository.PaisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaisDetalleViewModel @Inject constructor(
    private val repository: PaisRepository
) : ViewModel() {

    private val _pais = MutableStateFlow<Pais?>(null)
    val pais = _pais.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun cargarPais(nombre: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            android.util.Log.d("PaisDetalleVM", "════════════════════════════════════════")
            android.util.Log.d("PaisDetalleVM", "🔍 INICIANDO CARGA DE PAÍS")
            android.util.Log.d("PaisDetalleVM", "📝 Nombre recibido: '$nombre'")
            android.util.Log.d("PaisDetalleVM", "📏 Longitud del nombre: ${nombre.length}")
            android.util.Log.d("PaisDetalleVM", "🔤 Bytes del nombre: ${nombre.toByteArray().contentToString()}")

            try {
                android.util.Log.d("PaisDetalleVM", "🌐 Llamando al repositorio...")
                val pais = repository.getPais(nombre)

                android.util.Log.d("PaisDetalleVM", "✅ PAÍS RECIBIDO DEL REPOSITORIO:")
                android.util.Log.d("PaisDetalleVM", "   - Nombre: ${pais.nombre}")
                android.util.Log.d("PaisDetalleVM", "   - Nombre oficial: ${pais.nombreOficial}")
                android.util.Log.d("PaisDetalleVM", "   - Capital: ${pais.capital ?: "NULL"}")
                android.util.Log.d("PaisDetalleVM", "   - Región: '${pais.region}'")
                android.util.Log.d("PaisDetalleVM", "   - Subregión: '${pais.subregion}'")
                android.util.Log.d("PaisDetalleVM", "   - Población: ${pais.poblacion}")
                android.util.Log.d("PaisDetalleVM", "   - Área: ${pais.area}")
                android.util.Log.d("PaisDetalleVM", "   - Bandera URL: ${pais.banderaUrl ?: "NULL"}")
                android.util.Log.d("PaisDetalleVM", "   - Escudo URL: ${pais.escudoUrl ?: "NULL"}")
                android.util.Log.d("PaisDetalleVM", "   - Continente: '${pais.continente}'")
                android.util.Log.d("PaisDetalleVM", "   - Idiomas (${pais.idiomas.size}): ${pais.idiomas}")
                android.util.Log.d("PaisDetalleVM", "   - Monedas (${pais.monedas.size}): ${pais.monedas}")
                android.util.Log.d("PaisDetalleVM", "   - Gentilicio: '${pais.gentilicio}'")
                android.util.Log.d("PaisDetalleVM", "   - Mapa URL: ${pais.mapaUrl ?: "NULL"}")

                _pais.value = pais
                android.util.Log.d("PaisDetalleVM", "💾 Estado actualizado con el país")
            } catch (e: Exception) {
                android.util.Log.e("PaisDetalleVM", "❌ ERROR AL CARGAR PAÍS")
                android.util.Log.e("PaisDetalleVM", "   Tipo: ${e.javaClass.simpleName}")
                android.util.Log.e("PaisDetalleVM", "   Mensaje: ${e.message}")
                android.util.Log.e("PaisDetalleVM", "   Stack trace:", e)
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
                android.util.Log.d("PaisDetalleVM", "🏁 Carga finalizada (isLoading = false)")
                android.util.Log.d("PaisDetalleVM", "════════════════════════════════════════")
            }
        }
    }
}