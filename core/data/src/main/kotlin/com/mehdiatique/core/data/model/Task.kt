package com.mehdiatique.core.data.model

/**
 * Domain model representing a task.
 */
data class Task(
    val id: Long,
    val title: String,
    val description: String? = null,
    val isDone: Boolean,
    val priority: Int, // 0=none, 1=low, 2=med, 3=high (or use enum)
    val dueAt: Long? = null, // Deadline or reminder date
    val completedAt: Long? = null, // Set when task is marked done
    val createdAt: Long,
    val updatedAt: Long? = null,
    val note: Note? = null,
    val owner: Contact? = null,
)
