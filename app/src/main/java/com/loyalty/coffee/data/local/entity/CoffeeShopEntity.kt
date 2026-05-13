package com.loyalty.coffee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffee_shops")
data class CoffeeShopEntity(
    @PrimaryKey val id: String,
    val name: String,
    val address: String,
    val rating: Double,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val imageUrl: String? = null,
    val isConnected: Boolean = false
)
