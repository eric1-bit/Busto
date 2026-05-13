package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loyalty.coffee.ui.navigation.Screen
import com.loyalty.coffee.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val shops by viewModel.shops.collectAsState()
    val connected by viewModel.connectedShops.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Кофейни") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Подключённые", style = MaterialTheme.typography.titleMedium)
                if (connected.isEmpty()) {
                    Text(
                        "Сканируйте QR в кофейне, чтобы подключиться",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            items(connected) { shop ->
                ShopCard(shop.name, shop.address, shop.rating, true) {
                    navController.navigate(Screen.ShopDetail.createRoute(shop.id))
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Все кофейни", style = MaterialTheme.typography.titleMedium)
            }
            items(shops.filter { !it.isConnected }) { shop ->
                ShopCard(shop.name, shop.address, shop.rating, false) {
                    navController.navigate(Screen.ShopDetail.createRoute(shop.id))
                }
            }
        }
    }
}

@Composable
private fun ShopCard(
    name: String,
    address: String,
    rating: Double,
    isConnected: Boolean,
    onClick: () -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(name, style = MaterialTheme.typography.titleMedium)
                Text(address, style = MaterialTheme.typography.bodySmall)
                Text("★ $rating", style = MaterialTheme.typography.bodyMedium)
            }
            if (isConnected) {
                Icon(Icons.Default.QrCode, contentDescription = "Подключено")
            }
        }
    }
}
