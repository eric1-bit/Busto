package com.loyalty.coffee.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CoffeeColorScheme = lightColorScheme(
    primary = Coffee900,
    onPrimary = Coffee100,
    primaryContainer = Coffee800,
    onPrimaryContainer = Coffee50,
    secondary = Coffee600,
    onSecondary = Coffee50,
    secondaryContainer = Coffee200,
    onSecondaryContainer = Coffee900,
    tertiary = Coffee400,
    onTertiary = Coffee900,
    background = Coffee100,
    onBackground = Coffee900,
    surface = Coffee50,
    onSurface = Coffee900,
    surfaceVariant = Coffee200,
    onSurfaceVariant = Coffee800,
    outline = Coffee500,
    outlineVariant = Coffee300,
    error = Color(0xFF8B0000),
    onError = Coffee50
)

@Composable
fun CoffeeLoyaltyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CoffeeColorScheme,
        typography = CoffeeTypography,
        content = content
    )
}
