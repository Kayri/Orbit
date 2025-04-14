package com.mehdiatique.feature.tasks.presentation.details

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
import com.mehdiatique.core.data.model.Task
import com.mehdiatique.feature.tasks.presentation.details.components.EditSection
import com.mehdiatique.feature.tasks.presentation.details.components.ViewSection

/**
 * Main layout of the Task Detail screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailContent(
    state: TaskDetailState,
    snackbarHostState: SnackbarHostState,
    onEvent: (TaskDetailEvent) -> Unit,
    onUiEvent: (TaskDetailUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (state.mode) {
                            TaskDetailMode.ADD -> "New Task"
                            TaskDetailMode.EDIT -> "Edit Task"
                            TaskDetailMode.VIEW -> state.task.title
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (state.mode == TaskDetailMode.EDIT) onEvent(TaskDetailEvent.CloseEdit)
                        else onUiEvent(TaskDetailUiEvent.CloseScreen)

                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    if (state.mode.isEditable()) {
                        TextButton(
                            onClick = { onEvent(TaskDetailEvent.SaveTask) },
                            enabled = state.task.title.isNotBlank() == true
                        ) {
                            Text("Save")
                        }
                    } else {
                        IconButton(onClick = { onEvent(TaskDetailEvent.EditTask) }) {
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
                EditSection(task = state.task, contacts = state.contacts, onEvent = onEvent)
            } else {
                ViewSection(task = state.task, onEvent = onEvent)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TaskDetailContentPreview() {
    TaskDetailContent(
        state = TaskDetailState(
            mode = TaskDetailMode.VIEW,
            task = Task(
                id = 1L,
                title = "Title of the task",
                content = "This is the content of the task. Bla bla bla...",
                createdAt = 0,
            )
        ),
        snackbarHostState = SnackbarHostState(),
        onEvent = {},
        onUiEvent = {}
    )
}
