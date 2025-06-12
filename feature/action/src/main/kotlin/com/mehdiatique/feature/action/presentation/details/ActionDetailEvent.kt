package com.mehdiatique.feature.action.presentation.details

import com.mehdiatique.core.data.model.Priority

/**
 * Represents user-driven actions and changes in the Task Detail screen.
 *
 * These events are used to update UI state or trigger side effects like saving data,
 * navigating to related screens (tasks or tasks), or handling error visibility.
 */
sealed class ActionDetailEvent {
    data class TitleChanged(val title: String) : ActionDetailEvent()
    data class ContactChanged(val contactId: Long?) : ActionDetailEvent()
    data class PriorityChanged(val priority: Priority) : ActionDetailEvent()
    object CloseEdit : ActionDetailEvent()
    object EditAction : ActionDetailEvent()
    object SaveAction : ActionDetailEvent()
    object LoadAllContacts : ActionDetailEvent()
    data class OpenContact(val contactId: Long) : ActionDetailEvent()
    object AddInsight : ActionDetailEvent()
    data class OpenInsight(val insightId: Long) : ActionDetailEvent()
    object ErrorShown : ActionDetailEvent()
}