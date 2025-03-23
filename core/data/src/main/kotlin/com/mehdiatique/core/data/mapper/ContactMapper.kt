package com.mehdiatique.core.data.mapper

import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.core.database.entity.ContactEntity

/**
 * Extension function to convert a ContactEntity (from the database layer)
 * into a Contact domain model.
 */
fun ContactEntity.toDomain() = Contact(
    id = contactId,
    name = name,
    email = email,
    phone = phone,
    company = company,
    notes = notes,
    createdAt = createdAt
)

/**
 * Extension function to convert a Contact domain model into a ContactEntity.
 */
fun Contact.toEntity() = ContactEntity(
    contactId = id,
    name = name,
    email = email,
    phone = phone,
    company = company,
    notes = notes,
    createdAt = createdAt
)