package com.loyalty.coffee.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "promo_campaigns")
data class PromoCampaignEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val isActive: Boolean,
    val targetGroup: String? = null
)
