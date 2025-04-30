package com.practice.passwordmanageapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.practice.passwordmanageapp.data.PasswordDatabase
import com.practice.passwordmanageapp.navigation.AppNavGraph
import com.practice.passwordmanageapp.repository.PasswordRepository
import com.practice.passwordmanageapp.ui.theme.PasswordManageAppTheme
import com.practice.passwordmanageapp.viewmodel.PasswordViewModel
import com.practice.passwordmanageapp.viewmodel.PasswordViewModelFactory

class Main2Activity : ComponentActivity() {

    // This will automatically inject the ViewModel using the factory
    private val viewModel: PasswordViewModel by viewModels {
        PasswordViewModelFactory(PasswordRepository(PasswordDatabase.getDatabase(applicationContext).passwordDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { _, insets ->
            val updatedInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply system bar insets for a full-screen experience
            WindowInsetsCompat.Builder(insets).setInsets(
                WindowInsetsCompat.Type.systemBars(),
                updatedInsets
            ).build()
        }

        // Set the content view to Jetpack Compose UI
        setContent {
            PasswordManageAppTheme {
                val navController = rememberNavController() // Navigation controller for the app
                AppNavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}
