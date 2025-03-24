package com.mehdiatique.core.data.model

/**
 * Domain model representing a task.
 */
data class Task(
    val id: Long,
    val description: String?,
    val dueAt: Long?,
    val isDone: Boolean,
    val priority: Int,
    val title: String,
    val ownerId: Long?,
    val completedAt: Long?,
    val createdAt: Long,
    val updatedAt: Long?
)
