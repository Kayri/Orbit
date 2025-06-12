package com.mehdiatique.feature.action.presentation.details

/**
 * One-time UI events emitted by the Action Detail screen.
 *
 * These are used for navigation or transient actions such as closing the screen,
 * confirming a save, or navigating to related features like actions or actions.
 */
sealed class ActionDetailUiEvent {
    object CloseScreen : ActionDetailUiEvent()
    object ActionSaved : ActionDetailUiEvent()
    data class NavigateToContact(val contactId: Long) : ActionDetailUiEvent()
    object NavigateToAddInsight : ActionDetailUiEvent()
    data class NavigateToInsight(val insightId: Long) : ActionDetailUiEvent()
}
