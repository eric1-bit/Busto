package com.loyalty.coffee.data.local.entity

import androidx.room.Entity

@Entity(tableName = "balances", primaryKeys = ["userId", "coffeeShopId"])
data class BalanceEntity(
    val userId: String,
    val coffeeShopId: String,
    val stamps: Int = 0,
    val bonuses: Int = 0,
    val cashback: Double = 0.0,
    val visitsCount: Int = 0
)
