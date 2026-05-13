package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loyalty.coffee.ui.theme.*
import com.loyalty.coffee.ui.viewmodel.ScanResultViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopDetailScreen(
    shopId: String?,
    navController: NavController,
    viewModel: ScanResultViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()
    val shop by viewModel.shop.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()
    val hasPromo by viewModel.hasPromo.collectAsState()
    val balance by viewModel.balance.collectAsState()

    LaunchedEffect(shopId, user) {
        shopId?.let { sid ->
            user?.id?.let { uid ->
                viewModel.processQr("qr-cafe:$sid", uid)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        shop?.name ?: "Кофейня",
                        color = Coffee100
                    )
                },
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
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Coffee50
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Coffee700,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            shop?.address ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Coffee800
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Coffee400,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Рейтинг: ${shop?.rating}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Coffee900
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

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
                            "☕ Вы подключены",
                            style = MaterialTheme.typography.titleLarge,
                            color = Coffee900
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Баланс: $balance бонусов",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Coffee700
                        )
                    }
                }
            } else {
                if (hasPromo) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Coffee400.copy(alpha = 0.15f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "🎁 Акция для новых клиентов",
                                style = MaterialTheme.typography.titleMedium,
                                color = Coffee900
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "Бесплатный кофе при подключении!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Coffee800
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(
                    onClick = {
                        val uid = user?.id
                        if (shopId != null && uid != null) {
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
                        else "Стать клиентом кофейни",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
