package com.loyalty.coffee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val coffeeShopId: String,
    val type: String,
    val amount: Int,
    val description: String,
    val timestamp: Long
)
