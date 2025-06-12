package com.mehdiatique.feature.contacts.presentation.details

/**
 * One-time UI events emitted by the Contact Detail screen.
 *
 * These are used for navigation or transient actions such as closing the screen,
 * confirming a save, or navigating to related features like actions or insights.
 */
sealed class ContactDetailUiEvent {
    object CloseScreen : ContactDetailUiEvent()
    object ContactSaved : ContactDetailUiEvent()
    data class NavigateToAddAction(val contactId: Long) : ContactDetailUiEvent()
    data class NavigateToAddInsight(val contactId: Long) : ContactDetailUiEvent()
    data class NavigateToAction(val actionId: Long) : ContactDetailUiEvent()
    data class NavigateToInsight(val insightId: Long) : ContactDetailUiEvent()
}