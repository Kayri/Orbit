package com.mehdiatique.feature.contacts.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mehdiatique.orbit.design.theme.OrbitTheme

/**
 * A full-screen screen for adding or editing a contact.
 *
 * This screen mimics the Material Design 3 full-screen dialog pattern,
 * using a Scaffold with a top app bar and a content form.
 *
 * Errors are displayed via a Snackbar.
 *
 * @param viewModel Injected [ContactDetailViewModel] that manages the UI state and logic.
 * @param onClose Callback triggered when the user taps the close icon or completes saving.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    viewModel: ContactDetailViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(ContactDetailEvent.ErrorShown)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if (state.name.isBlank()) "New Contact" else "Edit Contact")
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    TextButton(
                        onClick = {
                            viewModel.onEvent(ContactDetailEvent.SaveContact)
                            onClose()
                        },
                        enabled = state.name.isNotBlank()
                    ) {
                        Text("Save")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        ContactDetailContent(
            state = state,
            onEvent = { viewModel.onEvent(it) },
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun ContactDetailContent(
    state: ContactDetailState,
    onEvent: (ContactDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        OutlinedTextField(
            value = state.name,
            onValueChange = { onEvent(ContactDetailEvent.NameChanged(it)) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.email,
            onValueChange = { onEvent(ContactDetailEvent.EmailChanged(it)) },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        OutlinedTextField(
            value = state.phone,
            onValueChange = { onEvent(ContactDetailEvent.PhoneChanged(it)) },
            label = { Text("Phone") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        OutlinedTextField(
            value = state.company,
            onValueChange = { onEvent(ContactDetailEvent.CompanyChanged(it)) },
            label = { Text("Company") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        OutlinedTextField(
            value = state.notes,
            onValueChange = { onEvent(ContactDetailEvent.NotesChanged(it)) },
            label = { Text("Notes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ContactDetailScreenPreview() {
    OrbitTheme {
        // Fake state to simulate the real one
        val fakeState = ContactDetailState(
            name = "Ada Lovelace",
            email = "ada@code.com",
            phone = "1234567890",
            company = "Analytical Engine Inc.",
            notes = "Loves math and programming."
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Edit Contact") },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    },
                    actions = {
                        TextButton(onClick = {}, enabled = fakeState.name.isNotBlank()) {
                            Text("Save")
                        }
                    }
                )
            }
        ) { innerPadding ->
            ContactDetailContent(
                state = fakeState,
                onEvent = {},
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            )
        }
    }
}