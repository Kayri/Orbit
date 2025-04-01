package com.mehdiatique.feature.contacts.presentation.details

/**
 * Represents user-driven actions and changes in the Contact Detail screen.
 *
 * These events are used to update UI state or trigger side effects like saving data,
 * navigating to related screens (notes or tasks), or handling error visibility.
 */
sealed class ContactDetailEvent {
    data class NameChanged(val name: String) : ContactDetailEvent()
    data class EmailChanged(val email: String) : ContactDetailEvent()
    data class PhoneChanged(val phone: String) : ContactDetailEvent()
    data class CompanyChanged(val company: String) : ContactDetailEvent()
    data class DescriptionChanged(val description: String) : ContactDetailEvent()

    object CloseEdit : ContactDetailEvent()
    object EditContact : ContactDetailEvent()
    object SaveContact : ContactDetailEvent()
    object AddNote : ContactDetailEvent()
    data class OpenNote(val noteId: Long) : ContactDetailEvent()
    object AddTask : ContactDetailEvent()
    data class OpenTask(val taskId: Long) : ContactDetailEvent()
    object ErrorShown : ContactDetailEvent()
}