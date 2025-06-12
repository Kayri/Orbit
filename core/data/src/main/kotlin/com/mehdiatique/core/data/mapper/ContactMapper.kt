package com.mehdiatique.core.data.mapper

import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.core.database.entity.ContactEntity
import com.mehdiatique.core.database.relationship.ContactWithRelations

/**
 * Maps [ContactWithRelations] to [Contact] domain model.
 *
 * Note: This mapping only converts the [ContactEntity] part.
 * Related actions and insights should be handled separately.
 */
fun ContactWithRelations.toDomain() = Contact(
    id = contact.contactId,
    name = contact.name,
    email = contact.email,
    phone = contact.phone,
    company = contact.company,
    description = contact.description,
    createdAt = contact.createdAt
)

/**
 * Maps [Contact] domain model to [ContactEntity] for database operations.
 */
fun Contact.toEntity() = ContactEntity(
    contactId = id,
    name = name,
    email = email,
    phone = phone,
    company = company,
    description = description,
    createdAt = createdAt
)
