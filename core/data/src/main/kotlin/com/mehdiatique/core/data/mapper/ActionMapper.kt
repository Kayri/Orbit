package com.mehdiatique.core.data.mapper

import com.mehdiatique.core.data.model.Priority
import com.mehdiatique.core.data.model.Action
import com.mehdiatique.core.database.entity.ActionEntity
import com.mehdiatique.core.database.relationship.ActionWithInsights

/**
 * Maps [ActionWithInsights] (including owner and linked note) to [Action] domain model.
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
    insights = insights.map { it.toDomain() }
)

/**
 * Maps [ActionEntity] to [Action] domain model without relations.
 */
fun ActionEntity.toDomain() = Action(
    id = actionId,
    title = title,
    isDone = isDone,
    priority = Priority.fromOrdinal(priority),
    dueAt = dueAt,
    completedAt = completedAt,
    createdAt = createdAt,
    updatedAt = updatedAt
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
