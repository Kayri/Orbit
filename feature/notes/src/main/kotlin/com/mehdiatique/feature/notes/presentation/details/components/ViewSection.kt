package com.mehdiatique.feature.notes.presentation.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mehdiatique.core.data.model.Note
import com.mehdiatique.feature.notes.presentation.details.NoteDetailEvent

/**
 * Read-only section that displays the note's information.
 */
@Composable
fun ViewSection(
    note: Note,
    onEvent: (NoteDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("title: " + note.title)
        Text("content: " + note.content)
        note.owner?.let { contact ->
            OwnerCard(name = contact.name, onClick = { onEvent(NoteDetailEvent.OpenContact(contact.id)) })
        }
    }
}


@Composable
fun OwnerCard(name: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Text("Owner: $name", modifier = Modifier.padding(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ViewSectionPreview() {
    ViewSection(
        note = Note(
            id = -1,
            content = "Content of the note",
            title = "Note Title",
            createdAt = 0,
        ),
        onEvent = {}
    )
}
