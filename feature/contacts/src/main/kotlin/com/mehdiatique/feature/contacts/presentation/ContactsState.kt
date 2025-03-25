package com.mehdiatique.feature.contacts.presentation

import com.mehdiatique.core.data.model.Contact

/**
 * Represents the UI state for the Contacts list feature.
 */
data class ContactsState(
    val contacts: List<Contact> = emptyList(),
    val filteredContacts: List<Contact> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
