package com.mehdiatique.feature.contacts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdiatique.core.data.repository.ContactRepository
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
 * ViewModel responsible for managing contact data, search filtering, and UI state
 * for the Contacts feature. It observes the contact list from the repository and
 * exposes a filtered version based on the current search query.
 *
 * This ViewModel follows an MVI-inspired pattern using:
 * - [ContactsState] as the single source of truth for UI state.
 * - [ContactsEvent] to handle user actions like search input.
 */
@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ContactsState(isLoading = true))
    val state: StateFlow<ContactsState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            contactRepository.getAllContacts()
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: e.cause?.message ?: "Unknown error"
                        )
                    }
                }
                .collect { contacts ->
                    _state.update { current ->
                        current.copy(
                            contacts = contacts,
                            isLoading = false
                        )
                    }
                }
        }
    }

    /**
     * Searches for contacts matching the given query and updates the UI state.
     *
     * Cancels any ongoing search to keep results up to date.
     *
     * @param query The search text entered by the user.
     */
    private fun searchContacts(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            contactRepository.searchContacts(query)
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: e.cause?.message ?: "Unknown error"
                        )
                    }
                }
                .collect { contacts ->
                    _state.update { current ->
                        current.copy(
                            contacts = contacts,
                            isLoading = false
                        )
                    }
                }
        }
    }
    private var searchJob: Job? = null

    /**
     * Processes user events from the UI and updates state accordingly.
     *
     * @param event The event triggered by user interaction.
     */
    fun onEvent(event: ContactsEvent) {
        when (event) {
            is ContactsEvent.SearchQueryChanged -> {
                _state.update { current -> current.copy(searchQuery = event.query) }
                searchContacts(event.query)
            }

            is ContactsEvent.ErrorShown -> {
                _state.update { it.copy(error = null) }
            }
        }
    }
}