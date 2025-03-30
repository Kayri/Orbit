package com.mehdiatique.feature.notes.presentation.details

/**
 * One-time UI events emitted by the Note Detail screen.
 *
 * These are used for navigation or transient actions such as closing the screen,
 * confirming a save, or navigating to related features like tasks or notes.
 */
sealed class NoteDetailUiEvent {
    object CloseScreen : NoteDetailUiEvent()
    object NoteSaved : NoteDetailUiEvent()
//    data class NavigateToAddTask(val contactId: Long) : NoteDetailUiEvent()
//    data class NavigateToAddNote(val contactId: Long) : NoteDetailUiEvent()
//    data class NavigateToTask(val taskId: Long) : NoteDetailUiEvent()
//    data class NavigateToNote(val noteId: Long) : NoteDetailUiEvent()
}