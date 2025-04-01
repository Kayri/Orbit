package com.mehdiatique.feature.contacts.presentation.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mehdiatique.core.data.model.Contact

/**
 * Editable fields for adding or updating a contact.
 */
@Composable
fun ContactDetailEditSection(
    contact: Contact?,
    onEvent: (ContactDetailEvent) -> Unit,
) {
    OutlinedTextField(
        value = contact?.name ?: "",
        onValueChange = { onEvent(ContactDetailEvent.NameChanged(it)) },
        label = { Text("Name") },
        modifier = Modifier.fillMaxWidth()
    )
    OutlinedTextField(
        value = contact?.email.orEmpty(),
        onValueChange = { onEvent(ContactDetailEvent.EmailChanged(it)) },
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
    OutlinedTextField(
        value = contact?.phone.orEmpty(),
        onValueChange = { onEvent(ContactDetailEvent.PhoneChanged(it)) },
        label = { Text("Phone") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
    OutlinedTextField(
        value = contact?.company.orEmpty(),
        onValueChange = { onEvent(ContactDetailEvent.CompanyChanged(it)) },
        label = { Text("Company") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
    OutlinedTextField(
        value = contact?.description.orEmpty(),
        onValueChange = { onEvent(ContactDetailEvent.DescriptionChanged(it)) },
        label = { Text("Description") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
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
 * Header row with title and add button, used for Notes and Tasks.
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
 * Temporary placeholder for future task/note items.
 */
@Composable
fun PlaceholderItem(title: String, content: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
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
fun ContactDetailEditSectionPreview() {
    Column {
        ContactDetailEditSection(
            contact = Contact(
                id = -1,
                name = "Ada Lovelace",
                email = "ada@code.com",
                phone = "123456789",
                company = "Engine Inc.",
                description = "Note about Ada",
                createdAt = 0
            ),
            onEvent = {}
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
