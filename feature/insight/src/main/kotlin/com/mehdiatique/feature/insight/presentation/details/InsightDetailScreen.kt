package com.mehdiatique.feature.insight.presentation.details

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
fun InsightDetailScreen(
    viewModel: InsightDetailViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onNavigateToContact: (Long) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current

    val sharedKey = state.insight.id.let { "insight-$it" }

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(InsightDetailEvent.ErrorShown)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is InsightDetailUiEvent.InsightSaved -> viewModel.onEvent(InsightDetailEvent.CloseEdit)
                is InsightDetailUiEvent.CloseScreen -> onClose()
                is InsightDetailUiEvent.NavigateToContact -> onNavigateToContact(event.contactId)
            }
        }
    }

    with(sharedTransitionScope) {
        InsightDetailContent(
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