package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.loyalty.coffee.ui.navigation.Screen
import com.loyalty.coffee.ui.theme.*

@Composable
fun OnboardingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Coffee100)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Coffee900),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocalCafe,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = Coffee100
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "QR-кафе",
            style = MaterialTheme.typography.displayLarge,
            color = Coffee900,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "Скачай приложение — получи бесплатный кофе\nНайди лучшие кофейни рядом с вузом",
            style = MaterialTheme.typography.bodyLarge,
            color = Coffee800,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = { navController.navigate(Screen.Auth.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Coffee900,
                contentColor = Coffee100
            )
        ) {
            Text("Начать", style = MaterialTheme.typography.titleLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Для студентов и любителей кофе",
            style = MaterialTheme.typography.labelSmall,
            color = Coffee600
        )
    }
}
