package com.mehdiatique.feature.assistant.presentation

import com.mehdiatique.feature.assistant.presentation.model.AssistantMessage


/**
 *
 */
data class AssistantState(
    val messages: List<AssistantMessage> = emptyList(),
    val currentInput: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
