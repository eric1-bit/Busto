package com.loyalty.coffee.data.local.dao

import androidx.room.*
import com.loyalty.coffee.data.local.entity.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {
    @Query("SELECT * FROM balances WHERE userId = :userId")
    fun getByUser(userId: String): Flow<List<BalanceEntity>>

    @Query("SELECT * FROM balances WHERE userId = :userId AND coffeeShopId = :shopId")
    suspend fun get(userId: String, shopId: String): BalanceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(balance: BalanceEntity)
}
