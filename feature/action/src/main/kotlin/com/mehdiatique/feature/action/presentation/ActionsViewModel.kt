package com.mehdiatique.feature.action.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdiatique.core.data.repository.ActionRepository
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
 * ViewModel responsible for managing action data, search filtering, and UI state
 * for the Actions feature. It observes the action list from the repository and
 * exposes a filtered version based on the current search query.
 *
 * This ViewModel follows an MVI-inspired pattern using:
 * - [ActionsState] as the single source of truth for UI state.
 * - [ActionsEvent] to handle user actions like search input.
 */
@HiltViewModel
class ActionsViewModel @Inject constructor(
    private val actionRepository: ActionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ActionsState(isLoading = true))
    val state: StateFlow<ActionsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            actionRepository.getAllActions()
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: e.cause?.message ?: "Unknown error"
                        )
                    }
                }
                .collect { actions ->
                    _state.update { current -> current.copy(actions = actions, isLoading = false) }
                }
        }
    }

    /**
     * Searches for actions matching the given query and updates the UI state.
     * Cancels any ongoing search to keep results up to date.
     *
     * @param query The search text entered by the user.
     */
    private fun searchActions(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            actionRepository.searchActions(query)
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: e.cause?.message ?: "Unknown error"
                        )
                    }
                }
                .collect { actions ->
                    _state.update { current -> current.copy(actions = actions, isLoading = false) }
                }
        }
    }

    private var searchJob: Job? = null

    /**
     * Processes user events from the UI and updates state accordingly.
     *
     * @param event The event triggered by user interaction.
     */
    fun onEvent(event: ActionsEvent) {
        when (event) {
            is ActionsEvent.SearchQueryChanged -> {
                _state.update { current -> current.copy(searchQuery = event.query) }
                searchActions(event.query)
            }

            is ActionsEvent.ErrorShown -> {
                _state.update { it.copy(error = null) }
            }
        }
    }
}