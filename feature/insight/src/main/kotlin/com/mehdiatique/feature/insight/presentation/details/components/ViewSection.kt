package com.mehdiatique.feature.insight.presentation.details.components

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
import com.mehdiatique.core.data.model.Insight
import com.mehdiatique.feature.insight.presentation.details.InsightDetailEvent

/**
 * Read-only section that displays the insight's information.
 */
@Composable
fun ViewSection(
    insight: Insight,
    onEvent: (InsightDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("content: " + insight.content)
        insight.owner?.let { contact ->
            OwnerCard(name = contact.name, onClick = { onEvent(InsightDetailEvent.OpenContact(contact.id)) })
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
        insight = Insight(
            id = -1,
            content = "Content of the insight",
            createdAt = 0,
        ),
        onEvent = {}
    )
}
