package com.mehdiatique.feature.contacts.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.core.data.repository.ActionRepository
import com.mehdiatique.core.data.repository.ContactRepository
import com.mehdiatique.core.data.repository.InsightRepository
import com.mehdiatique.core.navigation_contract.ContactNav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing contact creation, editing, and viewing logic.
 *
 * Handles loading of existing contacts, form updates, saving, and one-time UI events
 * such as navigation or showing messages.
 */
@HiltViewModel
class ContactDetailViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val insightRepository: InsightRepository,
    private val actionRepository: ActionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val contactId: Long? = savedStateHandle.get<String>(ContactNav.ARG_CONTACT_ID)?.toLong()

    private val _state = MutableStateFlow(
        ContactDetailState(
            mode = when {
                contactId == null -> ContactDetailMode.ADD
                else -> ContactDetailMode.VIEW
            },
            isLoading = contactId != null
        )
    )
    val state: StateFlow<ContactDetailState> = _state

    private val _uiEvent = MutableSharedFlow<ContactDetailUiEvent>()
    val uiEvent: SharedFlow<ContactDetailUiEvent> = _uiEvent

    init {
        contactId?.let { id -> observeContactDetails(id)}
    }

    /**
     * Handles user-driven events such as field changes, save, and cancel actions.
     *
     * @param event The event to process.
     */
    fun onEvent(event: ContactDetailEvent) {
        when (event) {
            is ContactDetailEvent.NameChanged -> updateContact { it.copy(name = event.name) }
            is ContactDetailEvent.EmailChanged -> updateContact { it.copy(email = event.email) }
            is ContactDetailEvent.PhoneChanged -> updateContact { it.copy(phone = event.phone) }
            is ContactDetailEvent.CompanyChanged -> updateContact { it.copy(company = event.company) }
            is ContactDetailEvent.DescriptionChanged -> updateContact { it.copy(description = event.description) }
            is ContactDetailEvent.CloseEdit -> _state.update { it.copy(mode = ContactDetailMode.VIEW) }
            is ContactDetailEvent.EditContact -> _state.update { it.copy(mode = ContactDetailMode.EDIT) }
            is ContactDetailEvent.SaveContact -> saveContact()
            is ContactDetailEvent.AddInsight -> _state.value.contact?.id?.let { contactId ->
                onUiEvent(ContactDetailUiEvent.NavigateToAddInsight(contactId))
            }

            is ContactDetailEvent.AddAction ->
                _state.value.contact?.id?.let { contactId ->
                    onUiEvent(ContactDetailUiEvent.NavigateToAddAction(contactId))
                }

            is ContactDetailEvent.OpenInsight -> onUiEvent(ContactDetailUiEvent.NavigateToInsight(event.insightId))
            is ContactDetailEvent.OpenAction -> onUiEvent(ContactDetailUiEvent.NavigateToAction(event.actionId))
            is ContactDetailEvent.ErrorShown -> _state.update { it.copy(error = null) }
        }
    }

    /**
     * Handles One-time UI events such as closing the screen, confirming a save,
     * or navigating to related features like actions or insights.
     *
     * @param event The event to process.
     */
    fun onUiEvent(event: ContactDetailUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    private fun observeContactDetails(id: Long) {
        viewModelScope.launch {
            contactDetailFlow(id)
                .onStart { _state.update { it.copy(isLoading = true) } }
                .catch { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: e.cause?.message ?: "Unknown error"
                        )
                    }
                }
                .collect { details ->
                    _state.update {
                        it.copy(
                            contact = details.contact,
                            actions = details.actions,
                            insights = details.insights,
                            isLoading = false
                        )
                    }
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun contactDetailFlow(id: Long): Flow<ContactDetails> =
        contactRepository.getContactById(id).flatMapLatest { contact ->
            val actionsFlow = actionRepository.getActionsForContact(id)
            val insightsFlow = insightRepository.getInsightsForContact(id)
            combine(
                flowOf(contact),
                actionsFlow,
                insightsFlow
            ) { contact, actions, insights ->
                ContactDetails(contact = contact, actions = actions, insights = insights)
            }
        }

    private fun updateContact(modify: (Contact) -> Contact) {
        val currentContact = _state.value.contact ?: Contact(
            id = TEMP_ID, // Auto-generated by Room
            createdAt = System.currentTimeMillis()
        )
        _state.update {
            it.copy(contact = modify(currentContact))
        }
    }


    private fun saveContact() {
        val contact = _state.value.contact ?: return
        viewModelScope.launch {
            try {
                if (contact.id == TEMP_ID) {
                    // New contact — insert and update state with the returned ID
                    val newId = contactRepository.addContact(contact)
                    val updatedContact = contact.copy(id = newId)
                    _state.update { it.copy(contact = updatedContact) }
                } else
                    contactRepository.updateContact(contact)

                onUiEvent(ContactDetailUiEvent.ContactSaved)
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message ?: e.cause?.message ?: "Unknown error") }
            }
        }
    }

    companion object {
        private const val TEMP_ID = 0L
    }
}
