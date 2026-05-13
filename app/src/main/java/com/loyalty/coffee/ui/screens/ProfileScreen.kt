package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.loyalty.coffee.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: AuthViewModel = hiltViewModel()) {
    val user by viewModel.user.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Профиль") }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text("Телефон: ${user?.phone ?: "Не авторизован"}")
            Text("ID: ${user?.id ?: "-"}")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Настройки уведомлений", style = MaterialTheme.typography.titleMedium)
            Text("О приложении", style = MaterialTheme.typography.titleMedium)
        }
    }
}
