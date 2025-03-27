package com.mehdiatique.feature.contacts.presentation.view

import com.mehdiatique.core.data.model.Contact

/**
 * Represents the UI state for the Contact View screen.
 *
 * @property contact The loaded contact, or null if not available.
 * @property isLoading True if the contact data is being loaded.
 * @property error An optional error message if loading failed.
 */
data class ContactViewState(
    val contact: Contact? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)