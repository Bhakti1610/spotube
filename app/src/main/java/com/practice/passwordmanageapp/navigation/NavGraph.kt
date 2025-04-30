package com.practice.passwordmanageapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.practice.passwordmanageapp.screens.AddPasswordScreen
import com.practice.passwordmanageapp.screens.EditPasswordScreen
import com.practice.passwordmanageapp.screens.HomeScreen
import com.practice.passwordmanageapp.viewmodel.PasswordViewModel

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Add : Screen("add")
    object Edit : Screen("edit/{passwordId}") {
        fun createRoute(passwordId: Int) = "edit/$passwordId"
    }
}

@Composable
fun AppNavGraph(navController: NavHostController, viewModel: PasswordViewModel) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController, viewModel)
        }
        composable(Screen.Add.route) {
            AddPasswordScreen(navController, viewModel)
        }
        composable(Screen.Edit.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("passwordId")?.toIntOrNull()
            EditPasswordScreen(navController, viewModel, id)
        }
    }
}