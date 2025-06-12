package com.mehdiatique.core.data.mapper

import com.mehdiatique.core.data.model.Insight
import com.mehdiatique.core.database.entity.InsightEntity

/**
 * Maps [InsightEntity] to [Insight] domain model.
 *
 * Note: This performs a direct mapping without resolving related owners or actions.
 */
fun InsightEntity.toDomain() = Insight(
    id = insightId,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
    actionId = actionOwnerId
)

/**
 * Maps [Insight] domain model to [InsightEntity] for database operations.
 */
fun Insight.toEntity() = InsightEntity(
    insightId = id,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
    contactOwnerId = owner?.id,
    actionOwnerId = actionId
)