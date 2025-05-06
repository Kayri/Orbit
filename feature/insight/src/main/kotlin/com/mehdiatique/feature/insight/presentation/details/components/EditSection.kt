package com.mehdiatique.feature.insight.presentation.details.components

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
import com.mehdiatique.core.data.model.Insight
import com.mehdiatique.feature.insight.presentation.details.InsightDetailEvent
import com.mehdiatique.orbit.design.components.DropdownSelector

/**
 * Editable fields for adding or updating a insight.
 */
@Composable
fun EditSection(
    insight: Insight,
    contacts: List<Contact>,
    onEvent: (InsightDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = insight.content,
            onValueChange = { onEvent(InsightDetailEvent.ContentChanged(it)) },
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
        )
        DropdownSelector(
            items = contacts,
            selected = insight.owner,
            onSelected = { contact ->
                onEvent(InsightDetailEvent.ContactChanged(contact))
            },
            itemLabel = { it.name },
            onDropdownOpened = { onEvent(InsightDetailEvent.LoadAllContacts) }
        ) { Text("Owner") }
    }
}

@Preview(showBackground = true)
@Composable
fun EditSectionPreview() {
    Column {
        EditSection(
            insight = Insight(
                id = -1,
                content = "Ada Lovelace",
                createdAt = 0,
            ),
            contacts = emptyList<Contact>(),
            onEvent = {}
        )
    }
}
