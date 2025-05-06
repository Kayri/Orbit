package com.mehdiatique.feature.insight.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdiatique.core.data.repository.InsightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing insight data, search filtering, and UI state
 * for the Insights feature. It observes the insight list from the repository and
 * exposes a filtered version based on the current search query.
 *
 * This ViewModel follows an MVI-inspired pattern using:
 * - [InsightsState] as the single source of truth for UI state.
 * - [InsightsEvent] to handle user actions like search input.
 */
@HiltViewModel
class InsightsViewModel @Inject constructor(
    private val insightRepository: InsightRepository
) : ViewModel() {

    private val _state = MutableStateFlow(InsightsState(isLoading = true))
    val state: StateFlow<InsightsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            insightRepository.getAllInsights()
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: e.cause?.message ?: "Unknown error"
                        )
                    }
                }
                .collect { insights ->
                    _state.update { current -> current.copy(insights = insights, isLoading = false) }
                }
        }
    }

    /**
     * Searches for insights matching the given query and updates the UI state.
     * Cancels any ongoing search to keep results up to date.
     *
     * @param query The search text entered by the user.
     */
    private fun searchInsights(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            insightRepository.searchInsights(query)
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: e.cause?.message ?: "Unknown error"
                        )
                    }
                }
                .collect { insights ->
                    _state.update { current -> current.copy(insights = insights, isLoading = false) }
                }
        }
    }

    private var searchJob: Job? = null

    /**
     * Processes user events from the UI and updates state accordingly.
     *
     * @param event The event triggered by user interaction.
     */
    fun onEvent(event: InsightsEvent) {
        when (event) {
            is InsightsEvent.SearchQueryChanged -> {
                _state.update { current -> current.copy(searchQuery = event.query) }
                searchInsights(event.query)
            }

            is InsightsEvent.ErrorShown -> {
                _state.update { it.copy(error = null) }
            }
        }
    }
}