package com.mehdiatique.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mehdiatique.core.database.entity.NoteEntity
import com.mehdiatique.core.database.relationship.NoteWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Transaction
    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteWithRelations>>

    @Transaction
    @Query("SELECT * FROM notes WHERE noteId = :id")
    fun getNoteById(id: Long): Flow<NoteWithRelations>

    @Transaction
    @Query("SELECT * FROM notes WHERE contactOwnerId = :contactId")
    fun getNotesForContact(contactId: Long): Flow<List<NoteWithRelations>>

    @Transaction
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    fun searchNotes(query: String): Flow<List<NoteWithRelations>>

    @Insert
    suspend fun insertNote(note: NoteEntity)

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("DELETE FROM notes WHERE noteId = :id")
    suspend fun deleteNoteById(id: Long)

}