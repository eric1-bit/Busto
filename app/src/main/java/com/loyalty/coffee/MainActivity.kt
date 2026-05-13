package com.loyalty.coffee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.loyalty.coffee.ui.navigation.Screen
import com.loyalty.coffee.ui.screens.*
import com.loyalty.coffee.ui.theme.CoffeeLoyaltyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeLoyaltyTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val showBar = currentDestination?.route !in listOf(
                    Screen.Onboarding.route, Screen.Auth.route, Screen.QRScanner.route, Screen.ScanResult.route
                )

                Scaffold(
                    bottomBar = {
                        if (showBar) {
                            NavigationBar {
                                val items = listOf(
                                    Triple(Screen.Home, "Кофейни", Icons.Default.Home),
                                    Triple(Screen.Wallet, "Кошелёк", Icons.Default.Wallet),
                                    Triple(Screen.QRScanner, "Сканер", Icons.Default.QrCodeScanner),
                                    Triple(Screen.Profile, "Профиль", Icons.Default.Person)
                                )
                                items.forEach { (screen, label, icon) ->
                                    NavigationBarItem(
                                        icon = { Icon(icon, contentDescription = label) },
                                        label = { Text(label) },
                                        selected = currentDestination?.hierarchy?.any {
                                            it.route == screen.route
                                        } == true,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Onboarding.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Onboarding.route) {
                            OnboardingScreen(navController)
                        }
                        composable(Screen.Auth.route) {
                            AuthScreen(navController)
                        }
                        composable(Screen.Home.route) {
                            HomeScreen(navController)
                        }
                        composable(Screen.Wallet.route) {
                            WalletScreen()
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen()
                        }
                        composable(Screen.QRScanner.route) {
                            QRScannerScreen(navController)
                        }
                        composable(Screen.ScanResult.route) { backStackEntry ->
                            val qrContent = backStackEntry.arguments?.getString("qrContent") ?: ""
                            ScanResultScreen(qrContent, navController)
                        }
                        composable(Screen.ShopDetail.route) { backStackEntry ->
                            val shopId = backStackEntry.arguments?.getString("shopId")
                            ShopDetailScreen(shopId, navController)
                        }
                    }
                }
            }
        }
    }
}
