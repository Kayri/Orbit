package com.mehdiatique.core.data.model

/**
 * Domain model representing a contact.
 */
data class Contact(
    val id: Long,
    val name: String = "",
    val email: String? = null,
    val phone: String? = null,
    val company: String? = null,
    val description: String? = null,
    val createdAt: Long = 0,
    val insights: List<Insight> = emptyList(),   // General memories
    val actions: List<Action> = emptyList()      // To-do/actions related to the contact
)
