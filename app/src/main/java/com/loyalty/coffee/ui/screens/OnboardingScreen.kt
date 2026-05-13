package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.loyalty.coffee.ui.navigation.Screen

@Composable
fun OnboardingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("QR-кафе", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Скачай приложение — получи бесплатный кофе\nНайди лучшие кофейни рядом с вузом",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navController.navigate(Screen.Auth.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Начать")
        }
    }
}
