package com.mehdiatique.core.data.repository

import com.mehdiatique.core.data.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for handling contact-related data operations.
 */
interface ContactRepository {
    fun getAllContacts(): Flow<List<Contact>>
    fun getContactById(id: Long): Flow<Contact>
    fun searchContacts(query: String): Flow<List<Contact>>
    suspend fun addContact(contact: Contact): Long
    suspend fun updateContact(contact: Contact)
    suspend fun deleteContactById(id: Long)
}