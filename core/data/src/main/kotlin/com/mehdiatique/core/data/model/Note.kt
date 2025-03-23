package com.mehdiatique.core.data.model

/**
 * Domain model representing a note.
 */
data class Note(
    val id: Long,
    val content: String,
    val title: String?,
    val ownerId: Long?,
    val createdAt: Long,
    val updatedAt: Long?
)
