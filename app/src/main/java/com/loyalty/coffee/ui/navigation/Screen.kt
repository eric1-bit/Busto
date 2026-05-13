package com.loyalty.coffee.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Auth : Screen("auth")
    object Home : Screen("home")
    object Wallet : Screen("wallet")
    object Profile : Screen("profile")
    object QRScanner : Screen("qr_scanner")
    object ScanResult : Screen("scan_result/{qrContent}") {
        fun createRoute(qrContent: String) = "scan_result/$qrContent"
    }
    object ShopDetail : Screen("shop_detail/{shopId}") {
        fun createRoute(shopId: String) = "shop_detail/$shopId"
    }
}
