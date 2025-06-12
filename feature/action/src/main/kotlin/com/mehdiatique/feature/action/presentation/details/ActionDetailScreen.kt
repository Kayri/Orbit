package com.mehdiatique.feature.action.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mehdiatique.core.ui_contract.ScreenUIConfig
import com.mehdiatique.feature.action.presentation.details.components.EditSection
import com.mehdiatique.feature.action.presentation.details.components.ViewSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionDetailScreen(
    viewModel: ActionDetailViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onNavigateToContact: (Long) -> Unit,
    onNavigateToAddInsight: () -> Unit,
    onNavigateToInsight: (Long) -> Unit,
    setConfig: (ScreenUIConfig) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.action, state.mode) {
        setConfig(
            ScreenUIConfig(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = when (state.mode) {
                                    ActionDetailMode.ADD -> "New Action"
                                    ActionDetailMode.EDIT -> "Edit Action"
                                    ActionDetailMode.VIEW -> state.action.title
                                }
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                if (state.mode == ActionDetailMode.EDIT) viewModel.onEvent(
                                    ActionDetailEvent.CloseEdit
                                )
                                else viewModel.onUiEvent(ActionDetailUiEvent.CloseScreen)

                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Close")
                            }
                        },
                        actions = {
                            if (state.mode.isEditable()) {
                                TextButton(
                                    onClick = { viewModel.onEvent(ActionDetailEvent.SaveAction) },
                                    enabled = state.action.title.isNotBlank()
                                ) {
                                    Text("Save")
                                }
                            } else {
                                IconButton(onClick = { viewModel.onEvent(ActionDetailEvent.EditAction) }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                                }
                            }
                        }
                    )
                },
                snackbarHostState = snackbarHostState
            )
        )
    }

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

    ActionDetailContent(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun ActionDetailContent(
    state: ActionDetailState,
    onEvent: (ActionDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        if (state.mode.isEditable()) {
            EditSection(state = state, onEvent = onEvent)
        } else {
            ViewSection(state = state, onEvent = onEvent)
        }
    }
}
