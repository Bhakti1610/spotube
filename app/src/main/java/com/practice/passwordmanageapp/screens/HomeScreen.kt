package com.practice.passwordmanageapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.practice.passwordmanageapp.R
import com.practice.passwordmanageapp.navigation.Screen
import com.practice.passwordmanageapp.viewmodel.PasswordViewModel

@Composable
fun HomeScreen(navController: NavHostController, viewModel: PasswordViewModel) {
    val passwords = viewModel.passwords.collectAsState(initial = emptyList()).value

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Password Manager") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.Add.route) },
                backgroundColor = colorResource(R.color.purple_500)
                ) {
                Text("+")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(passwords) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate(Screen.Edit.createRoute(item.id))
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Account: ${item.account}", style = MaterialTheme.typography.h6)
                        Text(text = "Username: ${item.username}", style = MaterialTheme.typography.body1)
                    }
                }
            }
        }
    }
}
