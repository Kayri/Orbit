package com.mehdiatique.feature.contacts.presentation.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdiatique.core.data.repository.ContactRepository
import com.mehdiatique.feature.contacts.navigation.ContactsRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the UI state and logic for the Contact View screen.
 *
 * When a contact ID is present in the SavedStateHandle, it loads that contact
 * for viewing. Otherwise, the state remains empty.
 *
 * It also handles user events such as the creation of a new task or note.
 *
 * @property contactRepository The repository used to load contact data.
 * @property savedStateHandle The SavedStateHandle that may contain a "contactId" used to load an existing contact.
 */
@HiltViewModel
class ContactViewViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val contactId: Long? = savedStateHandle.get<String>(ContactsRoute.View.ARG_ID)?.toLong()

    private val _state = MutableStateFlow(ContactViewState(isLoading = true))
    val state: StateFlow<ContactViewState> = _state

    init {
        contactId?.let { id ->
            viewModelScope.launch {
                contactRepository.getContactById(id)
                    .onStart { _state.update { it.copy(isLoading = true) } }
                    .catch { e ->
                        _state.update { it.copy(isLoading = false, error = e.message) }
                    }
                    .collect { contact ->
                        _state.value = ContactViewState(contact = contact, isLoading = false)
                    }
            }
        } ?: run {
            _state.value = ContactViewState(isLoading = false, error = "No contact found")
        }
    }

    /**
     * Handles user-driven events such as field changes, save, and cancel actions.
     *
     * @param event The event to process.
     */
    fun onEvent(event: ContactViewEvent) {
        when (event) {
            is ContactViewEvent.AddTask -> {
                // TODO: Handle navigation to add a new task for this contact
            }

            is ContactViewEvent.AddNote -> {
                // TODO: Handle navigation to add a new note for this contact
            }

            is ContactViewEvent.EditContact -> {
                // TODO: Trigger navigation to the edit contact screen (ContactDetailScreen)
            }

            is ContactViewEvent.ErrorShown -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

}
