package com.app.examenapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.examenapp.presentation.screens.home.HomeScreen

/**
 * Define las rutas (pantallas) de la aplicación.
 * Las rutas deben ser únicas.
 */
sealed class Screen(
    val route: String,
) {
    // Pantalla principal de la lista de países
    object Home : Screen("HomeScreen")

    // Pantalla de detalle de un país (preparada para el futuro)
    object Detail : Screen("pais_detail/{paisNombre}") {
        // Función helper para construir la ruta con el argumento
        fun createRoute(paisNombre: String) = "pais_detail/$paisNombre"
    }
}

/**
 * Define el grafo de navegación de la aplicación de países.
 *
 * @param modifier Modificador de Compose para el NavHost.
 * @param navController Controlador de navegación de Compose.
 */
@Composable
fun PaisesNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        // Pantalla de Inicio (Lista de Países)
        composable(route = Screen.Home.route) {
            HomeScreen(
                onPaisClick = { paisNombre ->
                    // Navegar a la pantalla de detalle cuando esté implementada
                    // navController.navigate(Screen.Detail.createRoute(paisNombre))
                    println("Navegando a detalle de: $paisNombre")
                },
            )
        }

        /*
        // Pantalla de Detalle (Descomentar cuando implementes DetailScreen)
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("paisNombre") { type = NavType.StringType }),
        ) { backStackEntry ->
            val paisNombre = backStackEntry.arguments?.getString("paisNombre") ?: ""

            // Reemplazar con tu DetalleScreen real
            // PaisDetailScreen(
            //     paisNombre = paisNombre,
            //     onBackClick = { navController.popBackStack() },
            // )
        }
        */
    }
}
