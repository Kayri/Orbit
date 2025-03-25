package com.mehdiatique.feature.contacts.presentation

/**
 * UI events for the Contacts list feature.
 */
sealed class ContactsEvent {
    data class SearchQueryChanged(val query: String) : ContactsEvent()
    object ErrorShown : ContactsEvent()
}
