package com.mehdiatique.core.data.repository

import com.mehdiatique.core.data.mapper.toDomain
import com.mehdiatique.core.data.mapper.toEntity
import com.mehdiatique.core.data.model.Insight
import com.mehdiatique.core.database.dao.InsightDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [InsightRepository] that delegates to [InsightDao]
 * for local data persistence using Room.
 */
@Singleton
class InsightRepositoryImpl @Inject constructor(
    private val insightDao: InsightDao
) : InsightRepository {
    override fun getAllInsights(): Flow<List<Insight>> =
        insightDao.getAllInsights().map { entities -> entities.map { it.toDomain() } }

    override fun getInsightById(id: Long): Flow<Insight> =
        insightDao.getInsightById(id = id).map { it.toDomain() }

    override fun getInsightsForContact(id: Long): Flow<List<Insight>> =
        insightDao.getInsightsForContact(contactId = id).map { entities -> entities.map { it.toDomain() } }

    override fun getInsightsForAction(id: Long): Flow<List<Insight>> =
        insightDao.getInsightsForAction(actionId = id).map { entities -> entities.map { it.toDomain() } }

    override fun searchInsights(query: String): Flow<List<Insight>> =
        insightDao.searchInsights(query = query).map { entities -> entities.map { it.toDomain() } }

    override suspend fun addInsight(insight: Insight): Long =
        insightDao.insertInsight(insight = insight.toEntity())

    override suspend fun updateInsight(insight: Insight) {
        insightDao.updateInsight(insight = insight.toEntity())
    }

    override suspend fun deleteInsightById(id: Long) {
        insightDao.deleteInsightById(id = id)
    }
}
