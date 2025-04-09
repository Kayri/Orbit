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
import com.mehdiatique.feature.notes.presentation.details.components.EditSection
import com.mehdiatique.feature.notes.presentation.details.components.ViewSection

/**
 * Main layout of the Note Detail screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailContent(
    state: NoteDetailState,
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
                        text = when (state.mode) {
                            NoteDetailMode.ADD -> "New Note"
                            NoteDetailMode.EDIT -> "Edit Note"
                            NoteDetailMode.VIEW -> state.note.title
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (state.mode == NoteDetailMode.EDIT) onEvent(NoteDetailEvent.CloseEdit)
                        else onUiEvent(NoteDetailUiEvent.CloseScreen)

                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    if (state.mode.isEditable()) {
                        TextButton(
                            onClick = { onEvent(NoteDetailEvent.SaveNote) },
                            enabled = state.note.title.isNotBlank() == true
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (state.mode.isEditable()) {
                EditSection(note = state.note, contacts = state.contacts, onEvent = onEvent)
            } else {
                ViewSection(note = state.note, onEvent = onEvent)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NoteDetailContentPreview() {
    NoteDetailContent(
        state = NoteDetailState(
            mode = NoteDetailMode.VIEW,
            note = Note(
                id = 1L,
                title = "Title of the note",
                content = "This is the content of the note. Bla bla bla...",
                createdAt = 0,
            )
        ),
        snackbarHostState = SnackbarHostState(),
        onEvent = {},
        onUiEvent = {}
    )
}
