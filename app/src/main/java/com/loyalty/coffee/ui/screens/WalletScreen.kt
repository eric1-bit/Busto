package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.loyalty.coffee.data.local.entity.BalanceEntity
import com.loyalty.coffee.data.local.entity.TransactionEntity
import com.loyalty.coffee.ui.theme.*
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
        if (userId.isNotEmpty()) QRGenerator.generate(userId, 400) else null
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "🎫 Кошелёк",
                        style = MaterialTheme.typography.headlineMedium,
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
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Coffee50
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Покажите кассиру",
                            style = MaterialTheme.typography.titleMedium,
                            color = Coffee900
                        )
                        Text(
                            "для начисления бонусов",
                            style = MaterialTheme.typography.bodySmall,
                            color = Coffee700
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .size(220.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Coffee100)
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (qrBitmap != null) {
                                Image(
                                    bitmap = qrBitmap.asImageBitmap(),
                                    contentDescription = "QR-код",
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.QrCode,
                                    contentDescription = null,
                                    modifier = Modifier.size(80.dp),
                                    tint = Coffee400
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "ID: ${userId.take(8)}...",
                            style = MaterialTheme.typography.labelSmall,
                            color = Coffee600
                        )
                    }
                }
            }

            if (balances.isNotEmpty()) {
                item {
                    Text(
                        "☕ Мои балансы",
                        style = MaterialTheme.typography.titleLarge,
                        color = Coffee900,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                items(balances) { bal ->
                    BalanceCard(bal)
                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "📋 История",
                    style = MaterialTheme.typography.titleLarge,
                    color = Coffee900,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
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
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Coffee50
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "Кофейня #${bal.coffeeShopId}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Coffee900
                )
                Text(
                    "${bal.visitsCount} визитов",
                    style = MaterialTheme.typography.bodySmall,
                    color = Coffee700
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (bal.bonuses > 0) {
                    BonusChip("★ ${bal.bonuses} бонусов", Coffee400)
                }
                if (bal.stamps > 0) {
                    BonusChip("⬤ ${bal.stamps} штампов", Coffee600)
                }
            }
        }
    }
}

@Composable
private fun BonusChip(text: String, color: androidx.compose.ui.graphics.Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

@Composable
private fun TransactionItem(tx: TransactionEntity) {
    val date = remember {
        SimpleDateFormat("dd MMM, HH:mm", Locale("ru")).format(Date(tx.timestamp))
    }

    val (icon, color) = when (tx.type) {
        "connect" -> "🔗" to Coffee600
        "promo" -> "🎁" to Coffee400
        "bonus" -> "★" to Coffee400
        else -> "•" to Coffee700
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Coffee50.copy(alpha = 0.7f)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                icon,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(end = 12.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    tx.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Coffee900
                )
                Text(
                    date,
                    style = MaterialTheme.typography.labelSmall,
                    color = Coffee600
                )
            }
            if (tx.amount > 0) {
                Text(
                    "+${tx.amount}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Coffee400
                )
            }
        }
    }
}
