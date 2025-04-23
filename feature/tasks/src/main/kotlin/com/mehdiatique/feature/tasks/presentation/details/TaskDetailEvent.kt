package com.mehdiatique.feature.tasks.presentation.details

import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.core.data.model.Priority

/**
 * Represents user-driven actions and changes in the Task Detail screen.
 *
 * These events are used to update UI state or trigger side effects like saving data,
 * navigating to related screens (tasks or tasks), or handling error visibility.
 */
sealed class TaskDetailEvent {
    data class ContentChanged(val content: String) : TaskDetailEvent()
    data class TitleChanged(val title: String) : TaskDetailEvent()
    data class ContactChanged(val contact: Contact?) : TaskDetailEvent()
    data class PriorityChanged(val priority: Priority) : TaskDetailEvent()
    object CloseEdit : TaskDetailEvent()
    object EditTask : TaskDetailEvent()
    object SaveTask : TaskDetailEvent()
    object LoadAllContacts : TaskDetailEvent()
    data class OpenContact(val contactId: Long) : TaskDetailEvent()
    object AddNote : TaskDetailEvent()
    data class OpenNote(val noteId: Long) : TaskDetailEvent()
    object ErrorShown : TaskDetailEvent()
}