package com.mehdiatique.feature.notes.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.core.data.model.Note
import com.mehdiatique.feature.notes.presentation.details.NoteDetailEvent
import com.mehdiatique.orbit.design.components.OwnerSelector

/**
 * Editable fields for adding or updating a note.
 */
@Composable
fun EditSection(
    note: Note?,
    contacts: List<Contact>,
    onEvent: (NoteDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
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
        )
        OwnerSelector(
            allContacts = contacts,
            selectedOwner = note?.owner,
            onOwnerSelected = { contact ->
                onEvent(NoteDetailEvent.ContactChanged(contact?.id))
            })
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
            contacts = listOf(),
            onEvent = {}
        )
    }
}
