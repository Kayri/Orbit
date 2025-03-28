package com.mehdiatique.feature.notes.presentation

import com.mehdiatique.core.data.model.Note

/**
 * Represents the UI state for the Notes list feature.
 */
data class NotesState(
    val notes: List<Note> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
