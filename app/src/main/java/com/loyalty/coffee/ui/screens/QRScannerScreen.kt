package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.loyalty.coffee.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRScannerScreen(navController: NavController) {
    var qrInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Сканер QR") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Введите QR-код кофейни",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Для теста введите: qr-cafe:1",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = qrInput,
                onValueChange = { qrInput = it },
                label = { Text("QR-код") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (qrInput.isNotBlank()) {
                        navController.navigate(Screen.ScanResult.createRoute(qrInput))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = qrInput.isNotBlank()
            ) {
                Text("Продолжить")
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Закрыть")
            }
        }
    }
}
