package com.mehdiatique.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mehdiatique.core.database.entity.InsightEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InsightDao {
    @Transaction
    @Query("SELECT * FROM insights")
    fun getAllInsights(): Flow<List<InsightEntity>>

    @Transaction
    @Query("SELECT * FROM insights WHERE insightId = :id")
    fun getInsightById(id: Long): Flow<InsightEntity>

    @Transaction
    @Query("SELECT * FROM insights WHERE contactOwnerId = :contactId")
    fun getInsightsForContact(contactId: Long): Flow<List<InsightEntity>>

    @Transaction
    @Query("SELECT * FROM insights WHERE actionOwnerId = :actionId")
    fun getInsightsForAction(actionId: Long): Flow<List<InsightEntity>>

    @Transaction
    @Query("SELECT * FROM insights WHERE content LIKE '%' || :query || '%'")
    fun searchInsights(query: String): Flow<List<InsightEntity>>

    @Insert
    suspend fun insertInsight(insight: InsightEntity): Long

    @Update
    suspend fun updateInsight(insight: InsightEntity)

    @Query("DELETE FROM insights WHERE insightId = :id")
    suspend fun deleteInsightById(id: Long)
}
