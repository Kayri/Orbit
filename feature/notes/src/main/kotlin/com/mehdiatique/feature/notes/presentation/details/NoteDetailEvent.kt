package com.mehdiatique.feature.notes.presentation.details

import com.mehdiatique.core.data.model.Contact

/**
 * Represents user-driven actions and changes in the Note Detail screen.
 *
 * These events are used to update UI state or trigger side effects like saving data,
 * navigating to related screens (notes or tasks), or handling error visibility.
 */
sealed class NoteDetailEvent {
    data class ContentChanged(val content: String) : NoteDetailEvent()
    data class TitleChanged(val title: String) : NoteDetailEvent()
    data class ContactChanged(val contact: Contact?) : NoteDetailEvent()
    object CloseEdit : NoteDetailEvent()
    object EditNote : NoteDetailEvent()
    object SaveNote : NoteDetailEvent()
    object LoadAllContacts : NoteDetailEvent()
    data class OpenContact(val contactId: Long) : NoteDetailEvent()
//    object AddTask : ContactDetailEvent()
//    data class OpenTask(val taskId: Long) : ContactDetailEvent()
    object ErrorShown : NoteDetailEvent()
}