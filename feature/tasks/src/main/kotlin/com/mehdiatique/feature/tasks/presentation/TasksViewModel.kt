package com.mehdiatique.feature.tasks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdiatique.core.data.repository.TaskRepository
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
 * ViewModel responsible for managing task data, search filtering, and UI state
 * for the Tasks feature. It observes the task list from the repository and
 * exposes a filtered version based on the current search query.
 *
 * This ViewModel follows an MVI-inspired pattern using:
 * - [TasksState] as the single source of truth for UI state.
 * - [TasksEvent] to handle user actions like search input.
 */
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TasksState(isLoading = true))
    val state: StateFlow<TasksState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.getAllTasks()
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: e.cause?.message ?: "Unknown error"
                        )
                    }
                }
                .collect { tasks ->
                    _state.update { current -> current.copy(tasks = tasks, isLoading = false) }
                }
        }
    }

    /**
     * Searches for tasks matching the given query and updates the UI state.
     * Cancels any ongoing search to keep results up to date.
     *
     * @param query The search text entered by the user.
     */
    private fun searchTasks(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            taskRepository.searchTasks(query)
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: e.cause?.message ?: "Unknown error"
                        )
                    }
                }
                .collect { tasks ->
                    _state.update { current -> current.copy(tasks = tasks, isLoading = false) }
                }
        }
    }

    private var searchJob: Job? = null

    /**
     * Processes user events from the UI and updates state accordingly.
     *
     * @param event The event triggered by user interaction.
     */
    fun onEvent(event: TasksEvent) {
        when (event) {
            is TasksEvent.SearchQueryChanged -> {
                _state.update { current -> current.copy(searchQuery = event.query) }
                searchTasks(event.query)
            }

            is TasksEvent.ErrorShown -> {
                _state.update { it.copy(error = null) }
            }
        }
    }
}