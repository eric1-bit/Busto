package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.loyalty.coffee.data.local.entity.BalanceEntity
import com.loyalty.coffee.data.local.entity.TransactionEntity
import com.loyalty.coffee.ui.util.QRGenerator
import com.loyalty.coffee.ui.viewmodel.WalletViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(viewModel: WalletViewModel = hiltViewModel()) {
    val user by viewModel.user.collectAsState()
    val balances by viewModel.balances.collectAsState()
    val transactions by viewModel.transactions.collectAsState()

    val userId = user?.id ?: ""
    val qrBitmap = remember(userId) {
        if (userId.isNotEmpty()) QRGenerator.generate(userId) else null
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Кошелёк") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Ваш QR-код для кассира",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Покажите этот экран для начисления/списания",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        if (qrBitmap != null) {
                            Image(
                                bitmap = qrBitmap.asImageBitmap(),
                                contentDescription = "QR-код пользователя",
                                modifier = Modifier.size(200.dp)
                            )
                        } else {
                            Text("—", style = MaterialTheme.typography.headlineMedium)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "ID: $userId",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }

                if (balances.isNotEmpty()) {
                    Text(
                        "Мои балансы",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            items(balances) { bal ->
                BalanceCard(bal)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "История операций",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(transactions) { tx ->
                TransactionItem(tx)
            }
        }
    }
}

@Composable
private fun BalanceCard(bal: BalanceEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Кофейня ID: ${bal.coffeeShopId}", style = MaterialTheme.typography.bodyLarge)
            Text(
                "Бонусы: ${bal.bonuses} • Штампы: ${bal.stamps} • Визиты: ${bal.visitsCount}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun TransactionItem(tx: TransactionEntity) {
    val date = remember {
        SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Date(tx.timestamp))
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(tx.description, style = MaterialTheme.typography.bodyLarge)
            Text(
                "$date • ${tx.type} • ${tx.amount}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
