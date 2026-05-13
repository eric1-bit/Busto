package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loyalty.coffee.ui.navigation.Screen
import com.loyalty.coffee.ui.theme.*
import com.loyalty.coffee.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val shops by viewModel.shops.collectAsState()
    val connected by viewModel.connectedShops.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "☕ Кофейни",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Coffee100
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Coffee900,
                    scrolledContainerColor = Coffee800
                )
            )
        },
        containerColor = Coffee100
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (connected.isNotEmpty()) {
                item {
                    Text(
                        "☕ Мои кофейни",
                        style = MaterialTheme.typography.titleLarge,
                        color = Coffee900
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(connected) { shop ->
                    CoffeeShopCard(
                        name = shop.name,
                        address = shop.address,
                        rating = shop.rating,
                        isConnected = true,
                        onClick = {
                            navController.navigate(Screen.ShopDetail.createRoute(shop.id))
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            item {
                Text(
                    "🌟 Все кофейни",
                    style = MaterialTheme.typography.titleLarge,
                    color = Coffee900
                )
            }

            items(shops.filter { !it.isConnected }) { shop ->
                CoffeeShopCard(
                    name = shop.name,
                    address = shop.address,
                    rating = shop.rating,
                    isConnected = false,
                    onClick = {
                        navController.navigate(Screen.ShopDetail.createRoute(shop.id))
                    }
                )
            }
        }
    }
}

@Composable
private fun CoffeeShopCard(
    name: String,
    address: String,
    rating: Double,
    isConnected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Coffee50
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Coffee900
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    address,
                    style = MaterialTheme.typography.bodySmall,
                    color = Coffee700
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Coffee400
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "$rating",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Coffee800
                    )
                }
            }
            if (isConnected) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Coffee200)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCode,
                        contentDescription = "Подключено",
                        tint = Coffee900,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
