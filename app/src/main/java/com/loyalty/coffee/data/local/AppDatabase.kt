package com.loyalty.coffee.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.loyalty.coffee.data.local.dao.*
import com.loyalty.coffee.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        CoffeeShopEntity::class,
        TransactionEntity::class,
        PromoCampaignEntity::class,
        BalanceEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun coffeeShopDao(): CoffeeShopDao
    abstract fun transactionDao(): TransactionDao
    abstract fun promoCampaignDao(): PromoCampaignDao
    abstract fun balanceDao(): BalanceDao
}
