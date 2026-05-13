package com.loyalty.coffee.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.loyalty.coffee.ui.theme.*
import com.loyalty.coffee.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: AuthViewModel = hiltViewModel()) {
    val user by viewModel.user.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль", color = Coffee100) },
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
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Coffee200),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = Coffee900
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            "Пользователь",
                            style = MaterialTheme.typography.titleLarge,
                            color = Coffee900
                        )
                        Text(
                            user?.phone ?: "Не авторизован",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Coffee700
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Настройки",
                style = MaterialTheme.typography.titleLarge,
                color = Coffee900
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Coffee50
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SettingItem("🔔 Уведомления", "Push-уведомления о бонусах")
                    Divider(color = Coffee200, modifier = Modifier.padding(vertical = 12.dp))
                    SettingItem("🎨 Тема", "Кофейная (активна)")
                    Divider(color = Coffee200, modifier = Modifier.padding(vertical = 12.dp))
                    SettingItem("ℹ️ О приложении", "Версия 1.0 (MVP)")
                }
            }
        }
    }
}

@Composable
private fun SettingItem(title: String, subtitle: String) {
    Column {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            color = Coffee900
        )
        Text(
            subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = Coffee700
        )
    }
}
