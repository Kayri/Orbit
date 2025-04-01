package com.mehdiatique.feature.contacts.presentation.details

import com.mehdiatique.core.data.model.Contact

/**
 * Represents the UI state of the Contact Detail screen.
 *
 * Holds the current contact data, screen mode (add, view, edit),
 * loading status, and any error messages.
 */
data class ContactDetailState(
    val contact: Contact? = null,
    val mode: ContactDetailMode = ContactDetailMode.ADD,
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * Enum representing the current mode of the Contact Detail screen.
 *
 * - [ADD]: User is creating a new contact.
 * - [VIEW]: User is viewing an existing contact.
 * - [EDIT]: User is editing an existing contact.
 */
enum class ContactDetailMode {
    ADD,
    VIEW,
    EDIT;

    /** Returns true if the screen is in a state where contact fields can be edited. */
    fun isEditable() = this == ADD || this == EDIT
}