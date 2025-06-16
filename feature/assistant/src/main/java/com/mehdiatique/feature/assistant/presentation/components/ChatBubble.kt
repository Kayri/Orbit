package com.mehdiatique.feature.assistant.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mehdiatique.feature.assistant.presentation.model.AssistantMessage
import com.mehdiatique.orbit.design.theme.OrbitTheme

@Composable
fun ChatBubble(
    message: AssistantMessage,
    isTyping: Boolean = false
) {
    val (text, isUser) = when (message) {
        is AssistantMessage.FromUser -> message.text to true
        is AssistantMessage.FromAssistant -> message.text to false
    }
    val backgroundColor = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val contentColor = if (isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    val alignment = if (isUser) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = alignment
    ) {
        Surface(
            color = backgroundColor,
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 2.dp
        ) {
            Text(
                text = if (isTyping) "Typing…" else text,
                modifier = Modifier.padding(12.dp),
                color = contentColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatBubblePreview() {
    OrbitTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            ChatBubble(
                message = AssistantMessage.FromAssistant("Hi! How can I help you today?")
            )
            ChatBubble(
                message = AssistantMessage.FromUser("Can you remind me to call Alice?")
            )
            ChatBubble(
                message = AssistantMessage.FromAssistant("Typing…"),
                isTyping = true
            )
        }
    }
}