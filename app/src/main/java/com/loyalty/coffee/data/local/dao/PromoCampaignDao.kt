package com.loyalty.coffee.data.local.dao

import androidx.room.*
import com.loyalty.coffee.data.local.entity.PromoCampaignEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PromoCampaignDao {
    @Query("SELECT * FROM promo_campaigns WHERE isActive = 1")
    fun getActive(): Flow<List<PromoCampaignEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(promos: List<PromoCampaignEntity>)
}
