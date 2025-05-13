package com.mehdiatique.feature.action.presentation.details

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
fun ActionDetailScreen(
    viewModel: ActionDetailViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onNavigateToContact: (Long) -> Unit,
    onNavigateToAddInsight: () -> Unit,
    onNavigateToInsight: (Long) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current

    val sharedKey = state.action.id.let { "action-$it" }

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(ActionDetailEvent.ErrorShown)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ActionDetailUiEvent.ActionSaved -> viewModel.onEvent(ActionDetailEvent.CloseEdit)
                is ActionDetailUiEvent.CloseScreen -> onClose()
                is ActionDetailUiEvent.NavigateToContact -> onNavigateToContact(event.contactId)
                is ActionDetailUiEvent.NavigateToAddInsight -> onNavigateToAddInsight()
                is ActionDetailUiEvent.NavigateToInsight -> onNavigateToInsight(event.insightId)
            }
        }
    }

    with(sharedTransitionScope) {
        ActionDetailContent(
            state = state,
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