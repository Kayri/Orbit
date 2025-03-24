package com.mehdiatique.core.data.mapper

import com.mehdiatique.core.data.model.Task
import com.mehdiatique.core.database.entity.TaskEntity

/**
 * Extension function to convert a TaskEntity (from the database layer)
 * into a Task domain model.
 */
fun TaskEntity.toDomain() = Task(
    id = taskId,
    description = description,
    dueAt = dueAt,
    isDone = isDone,
    priority = priority,
    title = title,
    ownerId = contactOwnerId,
    completedAt = completedAt,
    createdAt = createdAt,
    updatedAt = updatedAt
)

/**
 * Extension function to convert a Task domain model into a TaskEntity.
 */
fun Task.toEntity() = TaskEntity(
    taskId = id,
    description = description,
    dueAt = dueAt,
    isDone = isDone,
    priority = priority,
    title = title,
    contactOwnerId = ownerId,
    completedAt = completedAt,
    createdAt = createdAt,
    updatedAt = updatedAt
)