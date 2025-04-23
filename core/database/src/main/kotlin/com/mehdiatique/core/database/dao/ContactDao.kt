package com.mehdiatique.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mehdiatique.core.database.entity.ContactEntity
import com.mehdiatique.core.database.relationship.ContactWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Transaction
    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Flow<List<ContactWithRelations>>

    @Transaction
    @Query("SELECT * FROM contacts WHERE contactId = :id")
    fun getContactById(id: Long): Flow<ContactWithRelations>

    @Transaction
    @Query("SELECT * FROM contacts WHERE name LIKE '%' || :query || '%'")
    fun searchContacts(query: String): Flow<List<ContactWithRelations>>

    @Insert
    suspend fun insertContact(contact: ContactEntity): Long

    @Update
    suspend fun updateContact(contact: ContactEntity)

    @Query("DELETE FROM contacts WHERE contactId = :id")
    suspend fun deleteContactById(id: Long)
}
