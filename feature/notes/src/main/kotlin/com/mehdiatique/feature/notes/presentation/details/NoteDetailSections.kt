package com.mehdiatique.feature.notes.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mehdiatique.core.data.model.Note

/**
 * Editable fields for adding or updating a note.
 */
@Composable
fun NoteDetailEditSection(
    note: Note?,
    onEvent: (NoteDetailEvent) -> Unit,
) {
    OutlinedTextField(
        value = note?.title ?: "",
        onValueChange = { onEvent(NoteDetailEvent.TitleChanged(it)) },
        label = { Text("Name") },
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = note?.content.orEmpty(),
        onValueChange = { onEvent(NoteDetailEvent.ContentChanged(it)) },
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
    //Todo NoteDetailEvent.ContactChanged
}

/**
 * Read-only section that displays the note's information.
 */
@Composable
fun NoteInfoSection(note: Note) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(note.title)
        Text(note.content)
        note.ownerId?.let { Text("OwnerID: $it") }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteDetailEditSectionPreview() {
    Column {
        NoteDetailEditSection(
            note = Note(
                id = -1,
                content = "Ada Lovelace",
                title = "ada@code.com",
                ownerId = 1,
                createdAt = 0,
                updatedAt = 2
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoteInfoSectionPreview() {
    Column {
        NoteInfoSection(
            note = Note(
                id = -1,
                content = "Content of the note",
                title = "Note Title",
                ownerId = 1,
                createdAt = 0,
                updatedAt = 2
            ),
        )
    }
}
