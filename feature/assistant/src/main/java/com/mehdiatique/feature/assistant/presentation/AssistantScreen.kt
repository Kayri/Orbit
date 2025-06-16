package com.mehdiatique.feature.assistant.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mehdiatique.core.ui_contract.ScreenUIConfig
import com.mehdiatique.feature.assistant.presentation.components.ChatBubble
import com.mehdiatique.feature.assistant.presentation.model.AssistantMessage
import com.mehdiatique.orbit.design.theme.OrbitTheme

/**
 *
 */
@Composable
fun AssistantScreen(
    viewModel: AssistantViewModel = hiltViewModel(),
    setConfig: (ScreenUIConfig) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state: AssistantState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        setConfig(
            ScreenUIConfig(
                topBar = {},
                fab = {},
                snackbarHostState = snackbarHostState
            )
        )
    }

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(AssistantEvent.ErrorShown)
        }
    }

    AssistantScreenContent(
        state = state,
        onInputChanged = { viewModel.onEvent(AssistantEvent.InputChanged(it)) },
        onSubmit = { viewModel.onEvent(AssistantEvent.Submit(state.currentInput)) }
    )
}

/**
 *
 */
@Composable
fun AssistantScreenContent(
    state: AssistantState,
    onInputChanged: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(state.messages.reversed()) { message ->
                ChatBubble(message)
            }

            if (state.isLoading) {
                item {
                    ChatBubble(AssistantMessage.FromAssistant(""), isTyping = true)
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = state.currentInput,
                onValueChange = onInputChanged,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Ask something…") }
            )
            Button(onClick = onSubmit, enabled = state.currentInput.isNotBlank()) {
                Text("Send")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssistantScreenPreview() {
    OrbitTheme {
        AssistantScreenContent(
            state = AssistantState(
                currentInput = "What's next?",
                messages = listOf(
                    AssistantMessage.FromUser("Add a contact named Sarah"),
                    AssistantMessage.FromAssistant("What’s Sarah’s email?")
                ),
                isLoading = false
            ),
            onInputChanged = {},
            onSubmit = {}
        )
    }
}
