package com.mehdiatique.feature.action.presentation.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mehdiatique.core.data.model.Action
import com.mehdiatique.feature.action.presentation.details.ActionDetailEvent
import com.mehdiatique.feature.action.presentation.details.ActionDetailMode
import com.mehdiatique.feature.action.presentation.details.ActionDetailState

/**
 * Read-only section that displays the action's information.
 */
@Composable
fun ViewSection(
    state: ActionDetailState,
    onEvent: (ActionDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("title: " + state.action.title)
        state.owner?.let { contact ->
            CustomCard(onClick = { onEvent(ActionDetailEvent.OpenContact(contact.id)) }) {
                Text("Owner: ${contact.name}", modifier = Modifier.padding(8.dp))
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            state.insights.forEach { insight ->
                CustomCard(onClick = { onEvent(ActionDetailEvent.OpenInsight(insightId = insight.id)) }) {
                    Text("Insight: ${insight.content}", modifier = Modifier.padding(8.dp))
                }
            }
            IconButton(onClick = { onEvent(ActionDetailEvent.AddInsight) }) {
                Icon(Icons.Default.Add, contentDescription = "Add insight")
            }
        }
    }
}


@Composable
fun CustomCard(
    onClick: () -> Unit,
    label: @Composable (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .clickable { onClick() }
    ) { label }
}

@Preview(showBackground = true)
@Composable
fun ViewSectionPreview() {
    ViewSection(
        state = ActionDetailState(
            action = Action(
                id = -1,
                title = "Task Title",
                createdAt = 0,
            ),
            mode = ActionDetailMode.VIEW
        ),
        onEvent = {}
    )
}
