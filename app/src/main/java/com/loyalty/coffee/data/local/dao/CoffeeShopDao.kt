package com.loyalty.coffee.data.local.dao

import androidx.room.*
import com.loyalty.coffee.data.local.entity.CoffeeShopEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoffeeShopDao {
    @Query("SELECT * FROM coffee_shops")
    fun getAll(): Flow<List<CoffeeShopEntity>>

    @Query("SELECT * FROM coffee_shops WHERE isConnected = 1")
    fun getConnected(): Flow<List<CoffeeShopEntity>>

    @Query("UPDATE coffee_shops SET isConnected = 1 WHERE id = :id")
    suspend fun connect(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shops: List<CoffeeShopEntity>)
}
