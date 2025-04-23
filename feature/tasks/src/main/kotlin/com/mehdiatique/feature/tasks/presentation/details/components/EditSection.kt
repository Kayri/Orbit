package com.mehdiatique.feature.tasks.presentation.details.components

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
import com.mehdiatique.core.data.model.Task
import com.mehdiatique.feature.tasks.presentation.details.TaskDetailEvent
import com.mehdiatique.orbit.design.components.DropdownSelector

/**
 * Editable fields for adding or updating a task.
 */
@Composable
fun EditSection(
    task: Task,
    contacts: List<Contact>,
    onEvent: (TaskDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = task.title,
            onValueChange = { onEvent(TaskDetailEvent.TitleChanged(it)) },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = task.content.orEmpty(),
            onValueChange = { onEvent(TaskDetailEvent.ContentChanged(it)) },
            label = { Text("Content") },
            modifier = Modifier
                .fillMaxWidth()
        )

        DropdownSelector(
            items = Priority.entries.toList(),
            selected = task.priority,
            onSelected = { priority ->
                priority?.let { onEvent(TaskDetailEvent.PriorityChanged(it)) }
            },
            itemLabel = { it.label }
        ) { Text("Priority") }

//        DueDatePicker(
//            dueAt = currentTask.dueAt,
//            onDueDateSelected = { onEvent(TaskDetailEvent.DueDateChanged(it)) }
//        )

        DropdownSelector(
            items = contacts,
            selected = task.owner,
            onSelected = { contact ->
                onEvent(TaskDetailEvent.ContactChanged(contact))
            },
            itemLabel = { it.name },
            onDropdownOpened = { onEvent(TaskDetailEvent.LoadAllContacts) }
        ) { Text("Owner") }
    }
}

@Preview(showBackground = true)
@Composable
fun EditSectionPreview() {
    Column {
        EditSection(
            task = Task(
                id = -1,
                content = "Ada Lovelace",
                title = "ada@code.com",
                createdAt = 0,
            ),
            contacts = emptyList<Contact>(),
            onEvent = {}
        )
    }
}
