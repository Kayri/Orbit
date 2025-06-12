package com.mehdiatique.feature.contacts.presentation.details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.feature.contacts.presentation.details.ContactDetailEvent
import com.mehdiatique.feature.contacts.presentation.details.ContactDetailState

@Composable
fun ViewSection(
    state: ContactDetailState,
    onEvent: (ContactDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        state.contact?.let {
            ContactInfoSection(it)
            Spacer(Modifier.height(24.dp))

            SectionHeader("Insights", onAddClick = {
                onEvent(ContactDetailEvent.AddInsight)
            }, icon = Icons.Default.Add)

            state.insights
                .take(4)
                .forEach { insight ->
                    PlaceholderItem(
                        content = insight.content,
                        onClick = { onEvent(ContactDetailEvent.OpenInsight(insight.id)) }
                    )
                }

            Spacer(Modifier.height(24.dp))

            SectionHeader("Actions", onAddClick = {
                onEvent(ContactDetailEvent.AddAction)
            }, icon = Icons.Default.Add)

        }
    }
}

/**
 * Read-only section that displays the contact's information.
 */
@Composable
fun ContactInfoSection(contact: Contact) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        contact.email?.let { Text("📧 $it") }
        contact.phone?.let { Text("📞 $it") }
        contact.company?.let { Text("🏢 $it") }
        contact.description?.let { Text("📝 ${contact.description}") }
    }
}


/**
 * Header row with title and add button, used for Actions and Insights.
 */
@Composable
fun SectionHeader(title: String, onAddClick: () -> Unit, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        IconButton(onClick = onAddClick) {
            Icon(icon, contentDescription = "Add $title")
        }
    }
}

/**
 * Temporary placeholder for future actions/insights items.
 */
@Composable
fun PlaceholderItem(content: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}



@Preview(showBackground = true)
@Composable
fun ContactInfoSectionPreview() {
    Column {
        ContactInfoSection(
            contact = Contact(
                id = 2L,
                name = "Charles Babbage",
                email = "charles@calc.com",
                phone = "987654321",
                company = "Difference Engines",
                description = "Interested in machine design.",
                createdAt = 0
            )
        )
    }
}
