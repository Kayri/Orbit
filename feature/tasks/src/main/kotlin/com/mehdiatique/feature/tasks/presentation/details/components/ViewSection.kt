package com.mehdiatique.feature.tasks.presentation.details.components

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
import com.mehdiatique.core.data.model.Task
import com.mehdiatique.feature.tasks.presentation.details.TaskDetailEvent

/**
 * Read-only section that displays the task's information.
 */
@Composable
fun ViewSection(
    task: Task,
    onEvent: (TaskDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("title: " + task.title)
        Text("content: " + task.content)
        task.owner?.let { contact ->
            OwnerCard(name = contact.name, onClick = { onEvent(TaskDetailEvent.OpenContact(contact.id)) })
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
        task = Task(
            id = -1,
            content = "Content of the task",
            title = "Task Title",
            createdAt = 0,
        ),
        onEvent = {}
    )
}
