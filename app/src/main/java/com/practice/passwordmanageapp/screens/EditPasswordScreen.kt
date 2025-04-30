package com.practice.passwordmanageapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.practice.passwordmanageapp.utils.EncryptionUtils
import com.practice.passwordmanageapp.viewmodel.PasswordViewModel

@Composable
fun EditPasswordScreen(
    navController: NavHostController,
    viewModel: PasswordViewModel,
    id: Int?
) {
    val passwords = viewModel.passwords.collectAsState(initial = emptyList()).value
    val item = passwords.find { it.id == id } ?: return

    var account by remember { mutableStateOf(item.account) }
    var username by remember { mutableStateOf(item.username) }
    var password by remember { mutableStateOf(item.getDecryptedPassword()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Edit Password") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = account,
                onValueChange = { account = it },
                label = { Text("Account") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = {
                        viewModel.updatePassword(
                            item.copy(
                                account = account,
                                username = username,
                                encryptedPassword = EncryptionUtils.encrypt(password)
                            )
                        )
                        navController.popBackStack()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Update")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        viewModel.deletePassword(item)
                        navController.popBackStack()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.error,
                        contentColor = MaterialTheme.colors.onError
                    )
                ) {
                    Text("Delete")
                }
            }
        }
    }
}
