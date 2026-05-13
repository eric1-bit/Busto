package com.loyalty.coffee.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CoffeeScheme = lightColorScheme(
    primary = Color(0xFF6F4E37),
    onPrimary = Color.White,
    secondary = Color(0xFFD4A373),
    background = Color(0xFFFFFBF7),
    surface = Color.White,
    onSurface = Color(0xFF3E2723)
)

@Composable
fun CoffeeLoyaltyTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = CoffeeScheme, content = content)
}
