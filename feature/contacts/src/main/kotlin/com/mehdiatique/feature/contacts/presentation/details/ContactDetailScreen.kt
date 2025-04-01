package com.mehdiatique.feature.contacts.presentation.details

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope.ResizeMode
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mehdiatique.orbit.design.transition.LocalAnimatedVisibilityScope
import com.mehdiatique.orbit.design.transition.LocalSharedTransitionScope


@OptIn(ExperimentalSharedTransitionApi::class)
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

    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current

    val sharedKey = state.contact?.id?.let { "contact-$it" } ?: "fab"

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(ContactDetailEvent.ErrorShown)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ContactDetailUiEvent.ContactSaved -> viewModel.onEvent(ContactDetailEvent.CloseEdit)
                is ContactDetailUiEvent.CloseScreen -> onClose()
                is ContactDetailUiEvent.NavigateToAddNote -> onNavigateToAddNote(event.contactId)
                is ContactDetailUiEvent.NavigateToNote -> onNavigateToNote(event.noteId)
                is ContactDetailUiEvent.NavigateToAddTask -> onNavigateToAddTask(event.contactId)
                is ContactDetailUiEvent.NavigateToTask -> onNavigateToTask(event.taskId)
            }
        }
    }

    with(sharedTransitionScope) {
        ContactDetailContent(
            mode = state.mode,
            contact = state.contact,
            snackbarHostState = snackbarHostState,
            onEvent = viewModel::onEvent,
            onUiEvent = viewModel::onUiEvent,
            modifier = Modifier.sharedBounds(
                sharedContentState = rememberSharedContentState(key = sharedKey),
                animatedVisibilityScope = animatedVisibilityScope,
                enter = fadeIn(),
                exit = fadeOut(),
                resizeMode = ResizeMode.ScaleToBounds()
            )
        )
    }
}