package com.mehdiatique.core.data.repository

import com.mehdiatique.core.data.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for handling note-related data operations.
 */
interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    fun getNoteById(id: Long): Flow<Note>
    fun getNotesForContact(id: Long): Flow<List<Note>>
    fun searchNotes(query: String): Flow<List<Note>>
    suspend fun addNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNoteById(id: Long)
}
