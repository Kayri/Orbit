package com.mehdiatique.feature.insight.presentation.details

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
import com.mehdiatique.core.data.model.Insight
import com.mehdiatique.feature.insight.presentation.details.components.EditSection
import com.mehdiatique.feature.insight.presentation.details.components.ViewSection

/**
 * Main layout of the Insight Detail screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsightDetailContent(
    state: InsightDetailState,
    snackbarHostState: SnackbarHostState,
    onEvent: (InsightDetailEvent) -> Unit,
    onUiEvent: (InsightDetailUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
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
                        if (state.mode == InsightDetailMode.EDIT) onEvent(InsightDetailEvent.CloseEdit)
                        else onUiEvent(InsightDetailUiEvent.CloseScreen)

                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    if (state.mode.isEditable()) {
                        TextButton(
                            onClick = { onEvent(InsightDetailEvent.SaveInsight) },
                            enabled = state.insight.content.isNotBlank() == true
                        ) {
                            Text("Save")
                        }
                    } else {
                        IconButton(onClick = { onEvent(InsightDetailEvent.EditInsight) }) {
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
                EditSection(insight = state.insight, contacts = state.contacts, onEvent = onEvent)
            } else {
                ViewSection(insight = state.insight, onEvent = onEvent)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun InsightDetailContentPreview() {
    InsightDetailContent(
        state = InsightDetailState(
            mode = InsightDetailMode.VIEW,
            insight = Insight(
                id = 1L,
                content = "This is the content of the insight. Bla bla bla...",
                createdAt = 0,
            )
        ),
        snackbarHostState = SnackbarHostState(),
        onEvent = {},
        onUiEvent = {}
    )
}
