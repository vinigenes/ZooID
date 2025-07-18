package com.example.zooid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.zooid.screens.MainScreen
import com.example.zooid.screens.QuizScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainScreen(
                onStartQuiz = { id ->
                    navController.navigate("quiz/$id")

                }
            )
        }
        composable("quiz/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: -1
            QuizScreen(id = id, onExit = { navController.popBackStack() })

        }
    }
}