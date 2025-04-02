package com.mehdiatique.feature.notes.presentation.details.components

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
import com.mehdiatique.feature.notes.presentation.details.NoteDetailEvent

/**
 * Editable fields for adding or updating a note.
 */
@Composable
fun EditSection(
    note: Note?,
    onEvent: (NoteDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = note?.title ?: "",
            onValueChange = { onEvent(NoteDetailEvent.TitleChanged(it)) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = note?.content.orEmpty(),
            onValueChange = { onEvent(NoteDetailEvent.ContentChanged(it)) },
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        //Todo NoteDetailEvent.ContactChanged
    }
}

@Preview(showBackground = true)
@Composable
fun EditSectionPreview() {
    Column {
        EditSection(
            note = Note(
                id = -1,
                content = "Ada Lovelace",
                title = "ada@code.com",
                createdAt = 0,
            ),
            onEvent = {}
        )
    }
}