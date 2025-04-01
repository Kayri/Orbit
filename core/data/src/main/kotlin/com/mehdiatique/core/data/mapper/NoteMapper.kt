package com.mehdiatique.core.data.mapper

import com.mehdiatique.core.data.model.Note
import com.mehdiatique.core.database.entity.NoteEntity
import com.mehdiatique.core.database.relationship.NoteWithRelations

/**
 * Maps [NoteWithRelations] (including owner and linked task) to [Note] domain model.
 */
fun NoteWithRelations.toDomain() = Note(
    id = note.noteId,
    content = note.content,
    title = note.title,
    createdAt = note.createdAt,
    updatedAt = note.updatedAt,
    owner = owner?.toDomain(),
    task = task?.toDomain()
)

/**
 * Maps [NoteEntity] to [Note] domain model without relations.
 */
fun NoteEntity.toDomain() = Note(
    id = noteId,
    content = content,
    title = title,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

/**
 * Maps [Note] domain model to [NoteEntity] for database operations.
 */
fun Note.toEntity() = NoteEntity(
    noteId = id,
    content = content,
    title = title,
    createdAt = createdAt,
    updatedAt = updatedAt,
    contactOwnerId = owner?.id
)
