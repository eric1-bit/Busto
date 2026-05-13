package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
    val balance by viewModel.balance.collectAsState()

    LaunchedEffect(shopId, user) {
        shopId?.let { sid ->
            user?.id?.let { uid ->
                viewModel.processQr("qr-cafe:$sid", uid)
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(shop?.name ?: "Кофейня") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(shop?.address ?: "", style = MaterialTheme.typography.bodyLarge)
            Text("Рейтинг: ${shop?.rating}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(24.dp))

            if (isConnected) {
                Text("✅ Вы подключены", color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Баланс: $balance бонусов", style = MaterialTheme.typography.headlineSmall)
            } else {
                Button(
                    onClick = {
                        val uid = user?.id
                        if (shopId != null && uid != null) {
                            viewModel.confirm(uid, applyPromo = false)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Подключиться")
                }
            }
        }
    }
}
