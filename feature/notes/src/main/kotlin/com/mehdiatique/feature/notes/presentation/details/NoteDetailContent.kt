package com.mehdiatique.feature.notes.presentation.details

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
import com.mehdiatique.core.data.model.Note

/**
 * Main layout of the Note Detail screen.
 *
 * Displays the top bar and delegates the content to [NoteDetailBody],
 * which adapts to the current [NoteDetailMode].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailContent(
    mode: NoteDetailMode,
    note: Note?,
    snackbarHostState: SnackbarHostState,
    onEvent: (NoteDetailEvent) -> Unit,
    onUiEvent: (NoteDetailUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (mode) {
                            NoteDetailMode.ADD -> "New Note"
                            NoteDetailMode.EDIT -> "Edit Note"
                            NoteDetailMode.VIEW -> note?.title ?: "Note"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (mode == NoteDetailMode.EDIT) onEvent(NoteDetailEvent.CloseEdit)
                        else onUiEvent(NoteDetailUiEvent.CloseScreen)

                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    if (mode.isEditable()) {
                        TextButton(
                            onClick = { onEvent(NoteDetailEvent.SaveNote) },
                            enabled = note?.title?.isNotBlank() == true
                        ) {
                            Text("Save")
                        }
                    } else {
                        IconButton(onClick = { onEvent(NoteDetailEvent.EditNote) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        NoteDetailBody(
            mode = mode,
            note = note,
            onEvent = onEvent,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        )
    }
}

/**
 * Body content that switches between view and edit UI based on [NoteDetailMode].
 */
@Composable
fun NoteDetailBody(
    mode: NoteDetailMode,
    note: Note?,
    onEvent: (NoteDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (mode.isEditable()) {
            NoteDetailEditSection(note = note, onEvent = onEvent)
        } else {
            note?.let {
                NoteInfoSection(it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteDetailContentPreview() {
    NoteDetailContent(
        mode = NoteDetailMode.VIEW,
        note = Note(
            id = 1L,
            title = "Title of the note",
            content = "This is the content of the note. Bla bla bla...",
            ownerId = null,
            createdAt = 0,
            updatedAt = null
        ),
        snackbarHostState = SnackbarHostState(),
        onEvent = {},
        onUiEvent = {}
    )
}
