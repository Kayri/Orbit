package com.mehdiatique.feature.tasks.presentation.details

/**
 * One-time UI events emitted by the Task Detail screen.
 *
 * These are used for navigation or transient actions such as closing the screen,
 * confirming a save, or navigating to related features like tasks or tasks.
 */
sealed class TaskDetailUiEvent {
    object CloseScreen : TaskDetailUiEvent()
    object TaskSaved : TaskDetailUiEvent()
    data class NavigateToContact(val contactId: Long) : TaskDetailUiEvent()
//    data class NavigateToAddNote(val taskId: Long) : TaskDetailUiEvent()
//    data class NavigateToNote(val noteId: Long) : TaskDetailUiEvent()
}
