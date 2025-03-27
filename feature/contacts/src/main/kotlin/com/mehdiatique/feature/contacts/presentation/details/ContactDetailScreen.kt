package com.mehdiatique.feature.contacts.presentation.details

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun ContactDetailScreen(
    viewModel: ContactDetailViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onNavigateToAddNote: (Long) -> Unit,
    onNavigateToNote: (Long) -> Unit,
    onNavigateToAddTask: (Long) -> Unit,
    onNavigateToTask: (Long) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(ContactDetailEvent.ErrorShown)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ContactDetailUiEvent.ContactSaved -> {
                    viewModel.onEvent(ContactDetailEvent.CloseEdit)
                }

                is ContactDetailUiEvent.CloseScreen -> onClose()
                is ContactDetailUiEvent.NavigateToAddNote -> onNavigateToAddNote(event.contactId)
                is ContactDetailUiEvent.NavigateToNote -> onNavigateToNote(event.noteId)
                is ContactDetailUiEvent.NavigateToAddTask -> onNavigateToAddTask(event.contactId)
                is ContactDetailUiEvent.NavigateToTask -> onNavigateToTask(event.taskId)
            }
        }
    }

    ContactDetailContent(
        mode = state.mode,
        contact = state.contact,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        onUiEvent = viewModel::onUiEvent
    )
}