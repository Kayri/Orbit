package com.mehdiatique.core.data.repository

import com.mehdiatique.core.data.model.Insight
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for handling insight-related data operations.
 */
interface InsightRepository {
    fun getAllInsights(): Flow<List<Insight>>
    fun getInsightById(id: Long): Flow<Insight>
    fun getInsightsForContact(id: Long): Flow<List<Insight>>
    fun getInsightsForAction(id: Long): Flow<List<Insight>>
    fun searchInsights(query: String): Flow<List<Insight>>
    suspend fun addInsight(insight: Insight): Long
    suspend fun updateInsight(insight: Insight)
    suspend fun deleteInsightById(id: Long)
}
