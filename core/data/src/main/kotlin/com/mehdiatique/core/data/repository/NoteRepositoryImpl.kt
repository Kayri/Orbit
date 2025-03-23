package com.mehdiatique.core.data.repository

import com.mehdiatique.core.data.mapper.toDomain
import com.mehdiatique.core.data.mapper.toEntity
import com.mehdiatique.core.data.model.Note
import com.mehdiatique.core.database.dao.NoteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [NoteRepository] that delegates to [NoteDao]
 * for local data persistence using Room.
 */
@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository {
    override fun getNotesForContact(id: Long): Flow<List<Note>> =
        noteDao.getNotesForContact(contactId = id).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun addNote(note: Note) {
        noteDao.insertNote(note = note.toEntity())
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note = note.toEntity())
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note = note.toEntity())
    }
}