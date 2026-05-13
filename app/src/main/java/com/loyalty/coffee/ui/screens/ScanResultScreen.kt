package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loyalty.coffee.ui.theme.*
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
        topBar = {
            TopAppBar(
                title = { Text("Результат сканирования", color = Coffee100) },
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
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (shop == null) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "❌ QR-код не распознан",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Coffee900
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Убедитесь, что вы сканируете\nкод из кофейни-партнёра",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Coffee700,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
            } else {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Coffee200.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Coffee700
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    shop!!.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Coffee900,
                    textAlign = TextAlign.Center
                )
                Text(
                    shop!!.address,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Coffee700,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (isConnected) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Coffee200.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "☕ Уже подключены",
                                style = MaterialTheme.typography.titleLarge,
                                color = Coffee900
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Баланс: $balance бонусов",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Coffee700
                            )
                        }
                    }
                } else {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Coffee50
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Стать клиентом кофейни",
                                style = MaterialTheme.typography.titleLarge,
                                color = Coffee900
                            )
                            if (hasPromo) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Coffee400.copy(alpha = 0.15f))
                                        .padding(horizontal = 16.dp, vertical = 10.dp)
                                ) {
                                    Text(
                                        "🎁 Бесплатный кофе при подключении!",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Coffee800
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            user?.id?.let { uid ->
                                viewModel.confirm(uid, applyPromo = hasPromo)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Coffee900,
                            contentColor = Coffee100
                        )
                    ) {
                        Text(
                            if (hasPromo) "Подключиться и получить бесплатный кофе ☕"
                            else "Подключиться",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

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
                Text("Закрыть")
            }
        }
    }
}
