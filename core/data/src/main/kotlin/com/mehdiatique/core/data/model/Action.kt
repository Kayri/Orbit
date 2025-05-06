package com.mehdiatique.core.data.model

/**
 * Domain model representing an action (task/to-do).
 */
data class Action(
    val id: Long,
    val title: String = "",
    val isDone: Boolean = false,
    val priority: Priority = Priority.NONE,
    val dueAt: Long? = null,         // Deadline or reminder date
    val completedAt: Long? = null,   // Set when action is marked done
    val createdAt: Long,
    val updatedAt: Long? = null,
    val ownerId: Long? = null,
    val insights: List<Insight> = emptyList()  // Context/memories for this action
)
