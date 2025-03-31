package com.mehdiatique.core.data.model

/**
 * Domain model representing a contact.
 */
data class Contact(
    val id: Long,
    val name: String,
    val email: String? = null,
    val phone: String? = null,
    val company: String? = null,
    val description: String? = null,
    val createdAt: Long,
    val noteList: List<Note>? = null,
    val taskList: List<Task>? = null
)
