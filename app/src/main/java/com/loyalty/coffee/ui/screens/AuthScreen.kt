package com.loyalty.coffee.ui.screens
import com.loyalty.coffee.ui.navigation.Screen
import com.loyalty.coffee.ui.viewmodel.AuthViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loyalty.coffee.ui.navigation.Screen
import com.loyalty.coffee.ui.theme.*
import com.loyalty.coffee.ui.viewmodel.AuthViewModel

@Composable
fun AuthScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    var phone by remember { mutableStateOf("") }
    val user by viewModel.user.collectAsState()

    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Onboarding.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Coffee100)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = Coffee900
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            "Вход по телефону",
            style = MaterialTheme.typography.headlineMedium,
            color = Coffee900
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            "Введите номер, чтобы начать\nкопить бонусы",
            style = MaterialTheme.typography.bodyMedium,
            color = Coffee700,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = phone,
            onValueChange = { if (it.length <= 11) phone = it },
            label = { Text("Телефон", color = Coffee700) },
            placeholder = { Text("+7 999 000-00-00", color = Coffee400) },
            leadingIcon = {
                Icon(Icons.Default.Phone, contentDescription = null, tint = Coffee600)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Coffee900,
                unfocusedBorderColor = Coffee400,
                focusedContainerColor = Coffee50,
                unfocusedContainerColor = Coffee50
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { viewModel.register(phone) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Coffee900,
                contentColor = Coffee100
            ),
            enabled = phone.length >= 10
        ) {
            Text(
                "Получить код (MVP: без SMS)",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
