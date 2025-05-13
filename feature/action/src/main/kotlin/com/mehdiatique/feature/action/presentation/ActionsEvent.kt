package com.mehdiatique.feature.action.presentation

/**
 * UI events for the Actions list feature.
 */
sealed class ActionsEvent {
    data class SearchQueryChanged(val query: String) : ActionsEvent()
    object ErrorShown : ActionsEvent()
}
