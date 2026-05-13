package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loyalty.coffee.ui.viewmodel.ScanResultViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanResultScreen(
    qrContent: String,
    navController: NavController,
    viewModel: ScanResultViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val shop by viewModel.shop.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()
    val hasPromo by viewModel.hasPromo.collectAsState()
    val balance by viewModel.balance.collectAsState()

    LaunchedEffect(user) {
        user?.id?.let { viewModel.processQr(qrContent, it) }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Результат сканирования") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (shop == null) {
                Text("❌ QR-код не распознан", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Убедитесь, что вы ввели код из кофейни-партнёра")
            } else {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(shop!!.name, style = MaterialTheme.typography.headlineSmall)
                Text(shop!!.address, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(24.dp))

                if (isConnected) {
                    Text("✅ Вы уже подключены", color = MaterialTheme.colorScheme.primary)
                    Text("Ваш баланс: $balance бонусов", style = MaterialTheme.typography.titleMedium)
                } else {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Стать клиентом кофейни",
                                style = MaterialTheme.typography.titleMedium
                            )
                            if (hasPromo) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "🎁 Акция: бесплатный кофе при подключении!",
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            user?.id?.let { uid ->
                                viewModel.confirm(uid, applyPromo = hasPromo)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            if (hasPromo) "Подключиться и получить бесплатный кофе"
                            else "Подключиться"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Закрыть")
            }
        }
    }
}
