package com.app.examenapp.presentation.screens.detail
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.examenapp.domain.model.Pais
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaisDetalleScreen(
    nombrePais: String,
    onBack: () -> Unit,
    viewModel: PaisDetalleViewModel = hiltViewModel()
) {
    val pais by viewModel.pais.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(nombrePais) {
        android.util.Log.d("PaisDetalleScreen", "🎬 Iniciando carga de: $nombrePais")
        viewModel.cargarPais(nombrePais)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pais?.nombre ?: nombrePais) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // ✅ IMPORTANTE: Aplicar el padding aquí
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = error ?: "Error",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.cargarPais(nombrePais) }) {
                            Text("Reintentar")
                        }
                    }
                }
                pais != null -> {
                    DetalleContent(pais = pais!!)
                }
            }
        }
    }
}

@Composable
private fun DetalleContent(pais: Pais) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Bandera
        pais.banderaUrl?.let {
            Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                AsyncImage(
                    model = it,
                    contentDescription = "Bandera",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        // Nombre oficial
        CardInfo(titulo = "Nombre Oficial", valor = pais.nombreOficial)

        // Capital
        pais.capital?.let {
            CardInfo(titulo = "Capital", valor = it)
        }

        // Región
        if (pais.region.isNotEmpty()) {
            CardInfo(titulo = "Región", valor = pais.region)
        }

        // Subregión
        if (pais.subregion.isNotEmpty()) {
            CardInfo(titulo = "Subregión", valor = pais.subregion)
        }

        // Continente
        if (pais.continente.isNotEmpty()) {
            CardInfo(titulo = "Continente", valor = pais.continente)
        }

        // Población
        if (pais.poblacion > 0) {
            CardInfo(
                titulo = "Población",
                valor = formatNumber(pais.poblacion)
            )
        }

        // Área
        if (pais.area > 0) {
            CardInfo(
                titulo = "Área",
                valor = "${formatNumber(pais.area.toLong())} km²"
            )
        }

        // Gentilicio
        if (pais.gentilicio.isNotEmpty()) {
            CardInfo(titulo = "Gentilicio", valor = pais.gentilicio)
        }

        // Idiomas
        if (pais.idiomas.isNotEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Idiomas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    pais.idiomas.forEach { idioma ->
                        Text("• $idioma")
                    }
                }
            }
        }

        // Monedas
        if (pais.monedas.isNotEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Monedas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    pais.monedas.forEach { moneda ->
                        Text("• $moneda")
                    }
                }
            }
        }

        // Escudo
        pais.escudoUrl?.let {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Escudo de Armas",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = it,
                        contentDescription = "Escudo",
                        modifier = Modifier.size(150.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

@Composable
private fun CardInfo(titulo: String, valor: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = valor,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun formatNumber(number: Long): String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(number)
}