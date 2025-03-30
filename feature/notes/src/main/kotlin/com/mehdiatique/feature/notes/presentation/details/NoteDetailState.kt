package com.mehdiatique.feature.notes.presentation.details

import com.mehdiatique.core.data.model.Note
import com.mehdiatique.feature.notes.presentation.details.NoteDetailMode.ADD
import com.mehdiatique.feature.notes.presentation.details.NoteDetailMode.EDIT
import com.mehdiatique.feature.notes.presentation.details.NoteDetailMode.VIEW

/**
 * Represents the UI state of the Note Detail screen.
 *
 * Holds the current note data, screen mode (add, view, edit),
 * loading status, and any error messages.
 */
data class NoteDetailState(
    val note: Note? = null,
    val mode: NoteDetailMode = ADD,
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * Enum representing the current mode of the Note Detail screen.
 *
 * - [ADD]: User is creating a new note.
 * - [VIEW]: User is viewing an existing note.
 * - [EDIT]: User is editing an existing note.
 */
enum class NoteDetailMode {
    ADD,
    VIEW,
    EDIT;

    /** Returns true if the screen is in a state where note fields can be edited. */
    fun isEditable() = this == ADD || this == EDIT
}