package com.mehdiatique.feature.action.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mehdiatique.core.data.model.Action
import com.mehdiatique.feature.action.presentation.details.components.EditSection
import com.mehdiatique.feature.action.presentation.details.components.ViewSection

/**
 * Main layout of the Action Detail screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionDetailContent(
    state: ActionDetailState,
    snackbarHostState: SnackbarHostState,
    onEvent: (ActionDetailEvent) -> Unit,
    onUiEvent: (ActionDetailUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
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
                        if (state.mode == ActionDetailMode.EDIT) onEvent(ActionDetailEvent.CloseEdit)
                        else onUiEvent(ActionDetailUiEvent.CloseScreen)

                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    if (state.mode.isEditable()) {
                        TextButton(
                            onClick = { onEvent(ActionDetailEvent.SaveAction) },
                            enabled = state.action.title.isNotBlank() == true
                        ) {
                            Text("Save")
                        }
                    } else {
                        IconButton(onClick = { onEvent(ActionDetailEvent.EditAction) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (state.mode.isEditable()) {
                EditSection(state = state, onEvent = onEvent)
            } else {
                ViewSection(state = state, onEvent = onEvent)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionDetailContentPreview() {
    ActionDetailContent(
        state = ActionDetailState(
            mode = ActionDetailMode.VIEW,
            action = Action(
                id = 1L,
                title = "Title of the action",
                createdAt = 0,
            )
        ),
        snackbarHostState = SnackbarHostState(),
        onEvent = {},
        onUiEvent = {}
    )
}
