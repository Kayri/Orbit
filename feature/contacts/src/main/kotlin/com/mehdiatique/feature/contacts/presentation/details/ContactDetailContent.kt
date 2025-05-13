package com.mehdiatique.feature.contacts.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.feature.contacts.presentation.details.components.EditSection
import com.mehdiatique.feature.contacts.presentation.details.components.ViewSection

/**
 * Main layout of the Contact Detail screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailContent(
    state: ContactDetailState,
    snackbarHostState: SnackbarHostState,
    onEvent: (ContactDetailEvent) -> Unit,
    onUiEvent: (ContactDetailUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (state.mode) {
                            ContactDetailMode.ADD -> "New Contact"
                            ContactDetailMode.EDIT -> "Edit Contact"
                            ContactDetailMode.VIEW -> state.contact?.name ?: "Contact"
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (state.mode == ContactDetailMode.EDIT) onEvent(ContactDetailEvent.CloseEdit)
                        else onUiEvent(ContactDetailUiEvent.CloseScreen)

                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    if (state.mode.isEditable()) {
                        TextButton(
                            onClick = { onEvent(ContactDetailEvent.SaveContact) },
                            enabled = state.contact?.name?.isNotBlank() == true
                        ) {
                            Text("Save")
                        }
                    } else {
                        IconButton(onClick = { onEvent(ContactDetailEvent.EditContact) }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (state.mode.isEditable()) {
                EditSection(state = state, onEvent = onEvent)
            } else {
                ViewSection(state = state, onEvent = onEvent)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactDetailContentPreview() {
    ContactDetailContent(
        state = ContactDetailState(
            mode = ContactDetailMode.VIEW,
            contact = Contact(
                id = 1L,
                name = "Ada Lovelace",
                email = "ada@code.com",
                phone = "1234567890",
                company = "Analytical Engine Inc.",
                description = "Loves math and programming.",
                createdAt = System.currentTimeMillis()
            )
        ),
        snackbarHostState = SnackbarHostState(),
        onEvent = {},
        onUiEvent = {}
    )
}
