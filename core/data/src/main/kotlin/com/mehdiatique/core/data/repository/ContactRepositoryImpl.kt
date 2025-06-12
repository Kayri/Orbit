package com.mehdiatique.core.data.repository

import com.mehdiatique.core.data.mapper.toDomain
import com.mehdiatique.core.data.mapper.toEntity
import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.core.database.dao.ContactDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [ContactRepository] that delegates to [ContactDao]
 * for local data persistence using Room.
 */
@Singleton
class ContactRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao
) : ContactRepository {
    override fun getAllContacts(): Flow<List<Contact>> =
        contactDao.getAllContacts().map { entities -> entities.map { it.toDomain() } }

    override fun getContactById(id: Long): Flow<Contact> =
        contactDao.getContactById(id = id).map { it.toDomain() }

    override fun searchContacts(query: String): Flow<List<Contact>> =
        contactDao.searchContacts(query).map { entities -> entities.map { it.toDomain() } }

    override suspend fun addContact(contact: Contact): Long =
        contactDao.insertContact(contact = contact.toEntity())

    override suspend fun updateContact(contact: Contact) {
        contactDao.updateContact(contact = contact.toEntity())
    }

    override suspend fun deleteContactById(id: Long) {
        contactDao.deleteContactById(id = id)
    }
}