package com.mehdiatique.feature.insight.presentation

import com.mehdiatique.core.data.model.Insight

/**
 * Represents the UI state for the Insights list feature.
 */
data class InsightsState(
    val insights: List<Insight> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
