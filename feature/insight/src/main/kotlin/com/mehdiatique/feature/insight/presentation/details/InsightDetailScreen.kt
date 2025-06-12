package com.mehdiatique.feature.insight.presentation.details

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
import com.mehdiatique.feature.insight.presentation.details.components.EditSection
import com.mehdiatique.feature.insight.presentation.details.components.ViewSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightDetailScreen(
    viewModel: InsightDetailViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onNavigateToContact: (Long) -> Unit,
    setConfig: (ScreenUIConfig) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.insight, state.mode) {
        setConfig(
            ScreenUIConfig(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = when (state.mode) {
                                    InsightDetailMode.ADD -> "New Insight"
                                    InsightDetailMode.EDIT -> "Edit Insight"
                                    InsightDetailMode.VIEW -> "View Insight"
                                }
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                if (state.mode == InsightDetailMode.EDIT) viewModel.onEvent(
                                    InsightDetailEvent.CloseEdit
                                )
                                else viewModel.onUiEvent(InsightDetailUiEvent.CloseScreen)

                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Close")
                            }
                        },
                        actions = {
                            if (state.mode.isEditable()) {
                                TextButton(
                                    onClick = { viewModel.onEvent(InsightDetailEvent.SaveInsight) },
                                    enabled = state.insight.content.isNotBlank()
                                ) {
                                    Text("Save")
                                }
                            } else {
                                IconButton(onClick = { viewModel.onEvent(InsightDetailEvent.EditInsight) }) {
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

    InsightDetailContent(
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun InsightDetailContent(
    state: InsightDetailState,
    onEvent: (InsightDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
    ) {
        if (state.mode.isEditable()) {
            EditSection(insight = state.insight, contacts = state.contacts, onEvent = onEvent)
        } else {
            ViewSection(insight = state.insight, onEvent = onEvent)
        }
    }
}