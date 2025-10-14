package com.app.examenapp.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.examenapp.presentation.screens.home.components.PaisItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: PaisesListaViewModel = hiltViewModel(),
    onPaisClick: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showAboutDialog by remember { mutableStateOf(false) }

    // Filtrar países
    val paisesFiltrados = remember(state.paises, searchQuery) {
        if (searchQuery.isBlank()) {
            state.paises
        } else {
            state.paises.filter { pais ->
                pais.nombre.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Países") },
                actions = {
                    TextButton(onClick = { showAboutDialog = true }) {
                        Text("ℹInfo")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Buscador
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar país...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, "Buscar")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, "Limpiar")
                        }
                    }
                },
                singleLine = true
            )

            // Contenido
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    state.error != null -> {
                        Text(
                            text = "Error: ${state.error}",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }

                    paisesFiltrados.isEmpty() -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("No se encontraron países")
                        }
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (searchQuery.isNotBlank()) {
                                item {
                                    Text(
                                        text = "${paisesFiltrados.size} resultado(s)",
                                        style = MaterialTheme.typography.labelMedium,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                }
                            }

                            items(paisesFiltrados) { pais ->
                                PaisItem(
                                    pais = pais,
                                    onClick = { onPaisClick(pais.nombre) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo de información
    if (showAboutDialog) {
        AboutDialog(onDismiss = { showAboutDialog = false })
    }
}

@Composable
private fun AboutDialog(onDismiss: () -> Unit) {
    val infoText = """
ARQUITECTURA: MVVM + Clean Architecture

La aplicación utiliza MVVM (Model-View-ViewModel) combinado con Clean Architecture, separando el código en 3 capas:

1. Presentation (UI): Screens + ViewModels + nav
2. Domain: Modelo + usecases + 
3. Data: Llamadas a la Api, manejo de Caché y manejo de información
+1, Di: Construccion y configuración de dependencias


ESTRATEGIA DE GUARDADO

Se utiliza el caché para guardar los datos preferidos (consultados):

1. Lista completa de países en formato JSON
2. Timestamp de última actualización
3. Validación de caché (5 minutos)

Cuando se solicita un país:
- Si existe en caché CON datos completos -> se usa el caché
- Si solo tiene nombre -> se consulta la API
- Si no existe -> se consulta la API -> se guarda en caché 




ESTRATEGIA DE BÚSQUEDA

Búsqueda local en memoria:

1. Filtrado en tiempo real mientras escribes
2. (mayúsculas/minúsculas)
3. Busca en nombre común y oficial
4. Optimizada con remember()
5. Sin llamadas al la Api
    """.trimIndent()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Arquitectura de la App",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = infoText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}