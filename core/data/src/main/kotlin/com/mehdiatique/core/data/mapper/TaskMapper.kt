package com.mehdiatique.core.data.mapper

import com.mehdiatique.core.data.model.Priority
import com.mehdiatique.core.data.model.Task
import com.mehdiatique.core.database.entity.TaskEntity
import com.mehdiatique.core.database.relationship.TaskWithRelations

/**
 * Maps [TaskWithRelations] (including owner and linked note) to [Task] domain model.
 */
fun TaskWithRelations.toDomain() = Task(
    id = task.taskId,
    title = task.title,
    content = task.content,
    isDone = task.isDone,
    dueAt = task.dueAt,
    priority = Priority.fromOrdinal(task.priority),
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
    content = content,
    dueAt = dueAt,
    isDone = isDone,
    priority = Priority.fromOrdinal(priority),
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
    content = content,
    dueAt = dueAt,
    isDone = isDone,
    priority = priority.ordinal,
    title = title,
    completedAt = completedAt,
    createdAt = createdAt,
    updatedAt = updatedAt,
    contactOwnerId = owner?.id
)
