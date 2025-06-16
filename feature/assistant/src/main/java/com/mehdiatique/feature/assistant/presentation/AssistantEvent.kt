package com.mehdiatique.feature.assistant.presentation

/**
 *
 */
sealed class AssistantEvent {
    data class Submit(val query: String) : AssistantEvent()
    data class InputChanged(val value: String) : AssistantEvent()
    object ErrorShown : AssistantEvent()
}
