package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.loyalty.coffee.ui.navigation.Screen
import com.loyalty.coffee.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRScannerScreen(navController: NavController) {
    var qrInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Сканер QR", color = Coffee100) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Coffee900
                )
            )
        },
        containerColor = Coffee100
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Coffee200.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = null,
                    modifier = Modifier.size(56.dp),
                    tint = Coffee900
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Введите QR-код кофейни",
                style = MaterialTheme.typography.headlineSmall,
                color = Coffee900
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Например: qr-cafe:1",
                style = MaterialTheme.typography.bodyMedium,
                color = Coffee700
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = qrInput,
                onValueChange = { qrInput = it },
                label = { Text("QR-код", color = Coffee700) },
                placeholder = { Text("qr-cafe:1", color = Coffee400) },
                leadingIcon = {
                    Icon(Icons.Default.QrCodeScanner, null, tint = Coffee600)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Coffee900,
                    unfocusedBorderColor = Coffee400,
                    focusedContainerColor = Coffee50,
                    unfocusedContainerColor = Coffee50
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (qrInput.isNotBlank()) {
                        navController.navigate(Screen.ScanResult.createRoute(qrInput)) {
                            popUpTo(Screen.QRScanner.route) { inclusive = true }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Coffee900,
                    contentColor = Coffee100
                ),
                enabled = qrInput.isNotBlank()
            ) {
                Text(
                    "Продолжить",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedButtonDefaults.colors(
                    contentColor = Coffee900
                )
            ) {
                Text("Назад")
            }
        }
    }
}
