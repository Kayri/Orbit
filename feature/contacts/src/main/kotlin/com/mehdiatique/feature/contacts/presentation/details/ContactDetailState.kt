package com.mehdiatique.feature.contacts.presentation.details

import com.mehdiatique.core.data.model.Contact

data class ContactDetailState(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val company: String = "",
    val notes: String = "",
    val createdAt: Long? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    companion object {
        fun from(contact: Contact): ContactDetailState {
            return ContactDetailState(
                name = contact.name,
                email = contact.email ?: "",
                phone = contact.phone ?: "",
                company = contact.company ?: "",
                notes = contact.notes ?: "",
                createdAt = contact.createdAt
            )
        }
    }
}