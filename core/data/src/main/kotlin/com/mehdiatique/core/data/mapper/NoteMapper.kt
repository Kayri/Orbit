package com.mehdiatique.core.data.mapper

import com.mehdiatique.core.data.model.Note
import com.mehdiatique.core.database.entity.NoteEntity

/**
 * Extension function to convert a NoteEntity (from the database layer)
 * into a Note domain model.
 */
fun NoteEntity.toDomain() = Note(
    id = noteId,
    content = content,
    title = title,
    ownerId = contactOwnerId,
    createdAt = createdAt,
    updatedAt = updatedAt
)

/**
 * Extension function to convert a Note domain model into a NoteEntity.
 */
fun Note.toEntity() = NoteEntity(
    noteId = id,
    content = content,
    title = title,
    contactOwnerId = ownerId,
    createdAt = createdAt,
    updatedAt = updatedAt
)