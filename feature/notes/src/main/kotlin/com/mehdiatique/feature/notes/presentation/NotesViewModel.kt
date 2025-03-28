package com.mehdiatique.feature.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdiatique.core.data.repository.NoteRepository
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
 * ViewModel responsible for managing note data, search filtering, and UI state
 * for the Notes feature. It observes the note list from the repository and
 * exposes a filtered version based on the current search query.
 *
 * This ViewModel follows an MVI-inspired pattern using:
 * - [NotesState] as the single source of truth for UI state.
 * - [NotesEvent] to handle user actions like search input.
 */
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NotesState(isLoading = true))
    val state: StateFlow<NotesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            noteRepository.getAllNotes()
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: e.cause?.message ?: "Unknown error"
                        )
                    }
                }
                .collect { notes ->
                    _state.update { current -> current.copy(notes = notes, isLoading = false) }
                }
        }
    }

    /**
     * Searches for notes matching the given query and updates the UI state.
     * Cancels any ongoing search to keep results up to date.
     *
     * @param query The search text entered by the user.
     */
    private fun searchNotes(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            noteRepository.searchNotes(query)
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: e.cause?.message ?: "Unknown error"
                        )
                    }
                }
                .collect { notes ->
                    _state.update { current -> current.copy(notes = notes, isLoading = false) }
                }
        }
    }

    private var searchJob: Job? = null

    /**
     * Processes user events from the UI and updates state accordingly.
     *
     * @param event The event triggered by user interaction.
     */
    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.SearchQueryChanged -> {
                _state.update { current -> current.copy(searchQuery = event.query) }
                searchNotes(event.query)
            }

            is NotesEvent.ErrorShown -> {
                _state.update { it.copy(error = null) }
            }
        }
    }
}