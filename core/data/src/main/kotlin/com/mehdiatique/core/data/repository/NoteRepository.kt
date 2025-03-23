package com.mehdiatique.core.data.repository

import com.mehdiatique.core.data.model.Note
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for handling note-related data operations.
 */
interface NoteRepository {
    fun getNotesForContact(id: Long): Flow<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
}
