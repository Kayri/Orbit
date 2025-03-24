package com.mehdiatique.core.data.model

/**
 * Domain model representing a contact.
 */
data class Contact(
    val id: Long,
    val name: String,
    val email: String?,
    val phone: String?,
    val company: String?,
    val notes: String?,
    val createdAt: Long
)
