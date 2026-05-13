package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loyalty.coffee.ui.navigation.Screen
import com.loyalty.coffee.ui.viewmodel.AuthViewModel

@Composable
fun AuthScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    var phone by remember { mutableStateOf("") }
    val user by viewModel.user.collectAsState()

    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Onboarding.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Вход по телефону", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Телефон") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.register(phone) },
            modifier = Modifier.fillMaxWidth(),
            enabled = phone.length >= 10
        ) {
            Text("Войти (MVP: без SMS)")
        }
    }
}
