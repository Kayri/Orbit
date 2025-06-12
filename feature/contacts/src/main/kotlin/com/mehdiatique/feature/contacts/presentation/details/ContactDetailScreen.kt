package com.mehdiatique.feature.contacts.presentation.details

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
import com.mehdiatique.feature.contacts.presentation.details.components.EditSection
import com.mehdiatique.feature.contacts.presentation.details.components.ViewSection


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    viewModel: ContactDetailViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onNavigateToAddInsight: (Long) -> Unit,
    onNavigateToInsight: (Long) -> Unit,
    onNavigateToAddAction: (Long) -> Unit,
    onNavigateToAction: (Long) -> Unit,
    setConfig: (ScreenUIConfig) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.contact?.name, state.mode) {
        setConfig(
            ScreenUIConfig(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = when (state.mode) {
                                    ContactDetailMode.ADD -> "New Contact"
                                    ContactDetailMode.EDIT -> "Edit Contact"
                                    ContactDetailMode.VIEW -> state.contact?.name ?: "Contact"
                                },
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                if (state.mode == ContactDetailMode.EDIT) viewModel.onEvent(
                                    ContactDetailEvent.CloseEdit
                                )
                                else viewModel.onUiEvent(ContactDetailUiEvent.CloseScreen)

                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Close")
                            }
                        },
                        actions = {
                            if (state.mode.isEditable()) {
                                TextButton(
                                    onClick = { viewModel.onEvent(ContactDetailEvent.SaveContact) },
                                    enabled = state.contact?.name?.isNotBlank() == true
                                ) {
                                    Text("Save")
                                }
                            } else {
                                IconButton(onClick = { viewModel.onEvent(ContactDetailEvent.EditContact) }) {
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
            viewModel.onEvent(ContactDetailEvent.ErrorShown)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is ContactDetailUiEvent.ContactSaved -> viewModel.onEvent(ContactDetailEvent.CloseEdit)
                is ContactDetailUiEvent.CloseScreen -> onClose()
                is ContactDetailUiEvent.NavigateToAddAction -> onNavigateToAddAction(event.contactId)
                is ContactDetailUiEvent.NavigateToAction -> onNavigateToAction(event.actionId)
                is ContactDetailUiEvent.NavigateToAddInsight -> onNavigateToAddInsight(event.contactId)
                is ContactDetailUiEvent.NavigateToInsight -> onNavigateToInsight(event.insightId)
            }
        }
    }

    ContactDetailContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ContactDetailContent(
    state: ContactDetailState,
    onEvent: (ContactDetailEvent) -> Unit,
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
