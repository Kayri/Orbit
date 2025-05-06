package com.mehdiatique.feature.insight.presentation.details

import com.mehdiatique.core.data.model.Contact

/**
 * Represents user-driven actions and changes in the Insight Detail screen.
 *
 * These events are used to update UI state or trigger side effects like saving data,
 * navigating to related screens (insights or tasks), or handling error visibility.
 */
sealed class InsightDetailEvent {
    data class ContentChanged(val content: String) : InsightDetailEvent()
    data class ContactChanged(val contact: Contact?) : InsightDetailEvent()
    object CloseEdit : InsightDetailEvent()
    object EditInsight : InsightDetailEvent()
    object SaveInsight : InsightDetailEvent()
    object LoadAllContacts : InsightDetailEvent()
    data class OpenContact(val contactId: Long) : InsightDetailEvent()
    object ErrorShown : InsightDetailEvent()
}