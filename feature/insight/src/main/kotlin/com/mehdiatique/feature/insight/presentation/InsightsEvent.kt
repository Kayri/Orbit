package com.mehdiatique.feature.insight.presentation

/**
 * UI events for the Insights list feature.
 */
sealed class InsightsEvent {
    data class SearchQueryChanged(val query: String) : InsightsEvent()
    object ErrorShown : InsightsEvent()
}
