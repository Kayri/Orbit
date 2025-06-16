package com.mehdiatique.feature.assistant.presentation.model

sealed class AssistantMessage {
    data class FromUser(val text: String) : AssistantMessage()
    data class FromAssistant(val text: String) : AssistantMessage()
}