package com.mehdiatique.feature.notes.presentation

/**
 * UI events for the Notes list feature.
 */
sealed class NotesEvent {
    data class SearchQueryChanged(val query: String) : NotesEvent()
    object ErrorShown : NotesEvent()
}
