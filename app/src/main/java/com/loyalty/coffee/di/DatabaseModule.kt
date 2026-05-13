package com.loyalty.coffee.di

import android.content.Context
import androidx.room.Room
import com.loyalty.coffee.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "coffee_loyalty.db"
        ).build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Provides
    fun provideCoffeeShopDao(db: AppDatabase) = db.coffeeShopDao()

    @Provides
    fun provideTransactionDao(db: AppDatabase) = db.transactionDao()

    @Provides
    fun providePromoDao(db: AppDatabase) = db.promoCampaignDao()

    @Provides
    fun provideBalanceDao(db: AppDatabase) = db.balanceDao()
}
