package com.mehdiatique.core.data.mapper

import com.mehdiatique.core.data.model.Task
import com.mehdiatique.core.database.entity.TaskEntity
import com.mehdiatique.core.database.relationship.TaskWithRelations

/**
 * Maps [TaskWithRelations] (including owner and linked note) to [Task] domain model.
 */
fun TaskWithRelations.toDomain() = Task(
    id = task.taskId,
    title = task.title,
    description = task.description,
    isDone = task.isDone,
    dueAt = task.dueAt,
    priority = task.priority,
    completedAt = task.completedAt,
    createdAt = task.createdAt,
    updatedAt = task.updatedAt,
    note = note?.toDomain(),
    owner = owner?.toDomain()
)

/**
 * Maps [TaskEntity] to [Task] domain model without relations.
 */
fun TaskEntity.toDomain() = Task(
    id = taskId,
    description = description,
    dueAt = dueAt,
    isDone = isDone,
    priority = priority,
    title = title,
    completedAt = completedAt,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

/**
 * Maps [Task] domain model to [TaskEntity] for database operations.
 */
fun Task.toEntity() = TaskEntity(
    taskId = id,
    description = description,
    dueAt = dueAt,
    isDone = isDone,
    priority = priority,
    title = title,
    completedAt = completedAt,
    createdAt = createdAt,
    updatedAt = updatedAt,
    contactOwnerId = owner?.id
)
