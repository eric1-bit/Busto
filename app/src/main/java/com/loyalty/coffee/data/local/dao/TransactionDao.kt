package com.loyalty.coffee.data.local.dao

import androidx.room.*
import com.loyalty.coffee.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY timestamp DESC")
    fun getByUser(userId: String): Flow<List<TransactionEntity>>

    @Insert
    suspend fun insert(tx: TransactionEntity)
}
