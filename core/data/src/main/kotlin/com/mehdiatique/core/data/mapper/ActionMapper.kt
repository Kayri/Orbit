package com.mehdiatique.core.data.mapper

import com.mehdiatique.core.data.model.Priority
import com.mehdiatique.core.data.model.Action
import com.mehdiatique.core.database.entity.ActionEntity
import com.mehdiatique.core.database.relationship.ActionWithInsights

/**
 * Maps [ActionWithInsights] to [Action] domain model.
 *
 * Note: This mapping only converts the [ActionEntity] part.
 * Related insights and owner details should be handled separately.
 */
fun ActionWithInsights.toDomain() = Action(
    id = action.actionId,
    title = action.title,
    isDone = action.isDone,
    priority = Priority.fromOrdinal(action.priority),
    dueAt = action.dueAt,
    completedAt = action.completedAt,
    createdAt = action.createdAt,
    updatedAt = action.updatedAt,
    ownerId = action.contactOwnerId,
)

/**
 * Maps [Action] domain model to [ActionEntity] for database operations.
 */
fun Action.toEntity() = ActionEntity(
    actionId = id,
    contactOwnerId = ownerId,
    title = title,
    isDone = isDone,
    priority = priority.ordinal,
    dueAt = dueAt,
    completedAt = completedAt,
    createdAt = createdAt,
    updatedAt = updatedAt
)
