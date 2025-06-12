package com.mehdiatique.feature.contacts.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.feature.contacts.presentation.details.ContactDetailEvent
import com.mehdiatique.feature.contacts.presentation.details.ContactDetailState

/**
 * Editable fields for adding or updating a contact.
 */
@Composable
fun EditSection(
    state: ContactDetailState,
    onEvent: (ContactDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = state.contact?.name ?: "",
            onValueChange = { onEvent(ContactDetailEvent.NameChanged(it)) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.contact?.email.orEmpty(),
            onValueChange = { onEvent(ContactDetailEvent.EmailChanged(it)) },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        OutlinedTextField(
            value = state.contact?.phone.orEmpty(),
            onValueChange = { onEvent(ContactDetailEvent.PhoneChanged(it)) },
            label = { Text("Phone") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        OutlinedTextField(
            value = state.contact?.company.orEmpty(),
            onValueChange = { onEvent(ContactDetailEvent.CompanyChanged(it)) },
            label = { Text("Company") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        OutlinedTextField(
            value = state.contact?.description.orEmpty(),
            onValueChange = { onEvent(ContactDetailEvent.DescriptionChanged(it)) },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditSectionPreview() {
    EditSection(
        state = ContactDetailState(
            contact = Contact(
                id = -1,
                name = "Ada Lovelace",
                email = "ada@code.com",
                phone = "123456789",
                company = "Engine Inc.",
                description = "Note about Ada",
                createdAt = 0
            )
        ),
        onEvent = {}
    )
}