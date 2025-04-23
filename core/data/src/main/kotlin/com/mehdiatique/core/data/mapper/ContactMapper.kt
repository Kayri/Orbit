package com.mehdiatique.core.data.mapper

import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.core.database.entity.ContactEntity
import com.mehdiatique.core.database.relationship.ContactWithRelations

/**
 * Maps [ContactWithRelations] (including notes and tasks) to [Contact] domain model.
 */
fun ContactWithRelations.toDomain() = Contact(
    id = contact.contactId,
    name = contact.name,
    email = contact.email,
    phone = contact.phone,
    company = contact.company,
    description = contact.description,
    createdAt = contact.createdAt,
    insights = insights.map { it.toDomain() },
    actions = actions.map { it.toDomain() }
)

/**
 * Maps [ContactEntity] to [Contact] domain model without relations.
 */
fun ContactEntity.toDomain() = Contact(
    id = contactId,
    name = name,
    email = email,
    phone = phone,
    company = company,
    description = description,
    createdAt = createdAt,
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
