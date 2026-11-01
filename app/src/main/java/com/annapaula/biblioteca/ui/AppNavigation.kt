package com.annapaula.biblioteca.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val buscaViewModel: BuscaViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "busca",
        modifier = modifier
    ) {
        composable("busca") {
            BuscaScreen(
                viewModel = buscaViewModel,
                onLivroClick = { livro ->
                    navController.navigate("detalhes/${livro.idCurto()}")
                }
            )
        }

        composable(
            route = "detalhes/{livroId}",
            arguments = listOf(navArgument("livroId") { type = NavType.StringType })
        ) { backStackEntry ->
            val livroId = backStackEntry.arguments?.getString("livroId")
            DetalhesScreen(
                livro = buscaViewModel.livroPorId(livroId),
                onVoltar = { navController.popBackStack() }
            )
        }
    }
}