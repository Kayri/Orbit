package com.mehdiatique.feature.contacts.presentation.details

/**
 * Events for the Contact Detail screen.
 */
sealed class ContactDetailEvent {
    data class NameChanged(val name: String) : ContactDetailEvent()
    data class EmailChanged(val email: String) : ContactDetailEvent()
    data class PhoneChanged(val phone: String) : ContactDetailEvent()
    data class CompanyChanged(val company: String) : ContactDetailEvent()
    data class NotesChanged(val notes: String) : ContactDetailEvent()
    object SaveContact : ContactDetailEvent()
    object Cancel : ContactDetailEvent()
    object ErrorShown : ContactDetailEvent()
}