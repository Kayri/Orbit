package com.mehdiatique.feature.insight.presentation.details

/**
 * One-time UI events emitted by the Insight Detail screen.
 *
 * These are used for navigation or transient actions such as closing the screen,
 * confirming a save, or navigating to related features like actions or insights.
 */
sealed class InsightDetailUiEvent {
    object CloseScreen : InsightDetailUiEvent()
    object InsightSaved : InsightDetailUiEvent()
    data class NavigateToContact(val contactId: Long) : InsightDetailUiEvent()
}