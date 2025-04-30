package com.practice.passwordmanageapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.practice.passwordmanageapp.viewmodel.PasswordViewModel

@Composable
fun AddPasswordScreen(
    navController: NavHostController,
    viewModel: PasswordViewModel
) {
    var account by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var accountError by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    // Function to generate a strong password
    fun generateStrongPassword(length: Int = 12): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*()-_=+"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Add Password") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Account Field
            OutlinedTextField(
                value = account,
                onValueChange = {
                    account = it
                    accountError = false
                },
                isError = accountError,
                label = { Text("Account") },
                modifier = Modifier.fillMaxWidth()
            )
            if (accountError) {
                Text("Account is required", color = MaterialTheme.colors.error)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Username Field
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    usernameError = false
                },
                isError = usernameError,
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            if (usernameError) {
                Text("Username is required", color = MaterialTheme.colors.error)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false
                },
                isError = passwordError,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            if (passwordError) {
                Text("Password is required", color = MaterialTheme.colors.error)
            }

            // Generate Password Button
            Button(
                onClick = { password = generateStrongPassword() },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Generate Password")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Save Button
            Button(
                onClick = {
                    var valid = true
                    if (account.isBlank()) {
                        accountError = true
                        valid = false
                    }
                    if (username.isBlank()) {
                        usernameError = true
                        valid = false
                    }
                    if (password.isBlank()) {
                        passwordError = true
                        valid = false
                    }

                    if (valid) {
                        viewModel.addPassword(account, username, password)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}
