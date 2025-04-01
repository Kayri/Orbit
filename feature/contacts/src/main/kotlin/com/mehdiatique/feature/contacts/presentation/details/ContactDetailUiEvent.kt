package com.mehdiatique.feature.contacts.presentation.details

/**
 * One-time UI events emitted by the Contact Detail screen.
 *
 * These are used for navigation or transient actions such as closing the screen,
 * confirming a save, or navigating to related features like tasks or notes.
 */
sealed class ContactDetailUiEvent {
    object CloseScreen : ContactDetailUiEvent()
    object ContactSaved : ContactDetailUiEvent()
    data class NavigateToAddTask(val contactId: Long) : ContactDetailUiEvent()
    data class NavigateToAddNote(val contactId: Long) : ContactDetailUiEvent()
    data class NavigateToTask(val taskId: Long) : ContactDetailUiEvent()
    data class NavigateToNote(val noteId: Long) : ContactDetailUiEvent()
}