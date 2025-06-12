package com.mehdiatique.feature.action.presentation.details.components

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
import com.mehdiatique.core.data.model.Priority
import com.mehdiatique.core.data.model.Action
import com.mehdiatique.feature.action.presentation.details.ActionDetailEvent
import com.mehdiatique.feature.action.presentation.details.ActionDetailMode
import com.mehdiatique.feature.action.presentation.details.ActionDetailState
import com.mehdiatique.orbit.design.components.DropdownSelector

/**
 * Editable fields for adding or updating a action.
 */
@Composable
fun EditSection(
    state: ActionDetailState,
    onEvent: (ActionDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = state.action.title,
            onValueChange = { onEvent(ActionDetailEvent.TitleChanged(it)) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        DropdownSelector(
            items = Priority.entries.toList(),
            selected = state.action.priority,
            onSelected = { priority ->
                priority?.let { onEvent(ActionDetailEvent.PriorityChanged(it)) }
            },
            itemLabel = { it.label }
        ) { Text("Priority") }

//        DueDatePicker(
//            dueAt = currentTask.dueAt,
//            onDueDateSelected = { onEvent(TaskDetailEvent.DueDateChanged(it)) }
//        )

        DropdownSelector(
            items = state.contacts,
            selected = state.owner,
            onSelected = { contact ->
                onEvent(ActionDetailEvent.ContactChanged(contact?.id))
            },
            itemLabel = { it.name },
            onDropdownOpened = { onEvent(ActionDetailEvent.LoadAllContacts) }
        ) { Text("Owner") }
    }
}

@Preview(showBackground = true)
@Composable
fun EditSectionPreview() {
    Column {
        EditSection(
            state = ActionDetailState(
                action = Action(
                    id = -1,
                    title = "ada@code.com",
                    createdAt = 0,
                ),
                mode = ActionDetailMode.EDIT,
                contacts = emptyList<Contact>(),
            ),
            onEvent = {}
        )
    }
}
