package com.mehdiatique.feature.assistant.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehdiatique.feature.assistant.presentation.model.AssistantMessage
import com.mehdiatique.llamawrapper.LlamaBridge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 */
@HiltViewModel
class AssistantViewModel @Inject constructor(
) : ViewModel() {

    private val _state = MutableStateFlow(AssistantState(isLoading = true))
    val state: StateFlow<AssistantState> = _state.asStateFlow()

    init {
        val response = LlamaBridge.runPrompt("Hello from Kotlin!")
        _state.update {
            AssistantState(
                messages = listOf(
                    AssistantMessage.FromAssistant("Hi 👋 I'm your assistant. What can I help you with today?"),
                    AssistantMessage.FromAssistant(response),
                    )
            )
        }
    }

    /**
     * Processes user events from the UI and updates state accordingly.
     *
     * @param event The event triggered by user interaction.
     */
    fun onEvent(event: AssistantEvent) {
        when (event) {
            is AssistantEvent.InputChanged -> {
                _state.update { it.copy(currentInput = event.value) }
            }

            is AssistantEvent.Submit -> onSubmit()

            is AssistantEvent.ErrorShown -> {
                _state.update { it.copy(error = null) }
            }
        }
    }

    private fun onSubmit(){
        val input = _state.value.currentInput.trim()
        if (input.isBlank()) return

        _state.update {
            it.copy(
                messages = it.messages + AssistantMessage.FromUser(input),
                currentInput = "",
                isLoading = true
            )
        }

        viewModelScope.launch {
            delay(1500) // Simulate Ai process
            val parsed = mockAiParser(input)
            _state.update {
                it.copy(
                    messages = it.messages + AssistantMessage.FromAssistant(parsed),
                    isLoading = false
                )
            }
        }
    }
}

// Todo: Temporary function to mock ai parser
fun mockAiParser(input: String): String {
    return when {
        input.contains("contact", ignoreCase = true) -> "Looks like you're trying to create a contact."
        input.contains("action", ignoreCase = true) -> "This seems like an action to track."
        input.contains("insight", ignoreCase = true) -> "This could be stored as an insight."
        else -> "I'm not sure what to do with this yet. Want to add more details?"
    }
}