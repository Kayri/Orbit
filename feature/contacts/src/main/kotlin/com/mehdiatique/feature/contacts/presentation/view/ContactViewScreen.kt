package com.mehdiatique.feature.contacts.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.feature.contacts.presentation.ContactsEvent
import com.mehdiatique.feature.contacts.presentation.details.ContactDetailViewModel

/**
 * A full-screen screen for adding or editing a contact.
 *
 * This screen mimics the Material Design 3 full-screen dialog pattern,
 * using a Scaffold with a top app bar and a content form.
 *
 * Errors are displayed via a Snackbar.
 *
 * @param viewModel Injected [ContactViewViewModel] that manages the UI state and logic.
 * @param onClose Callback triggered when the user taps the close icon or completes saving.
 */
@Composable
fun ContactViewScreen(
    viewModel: ContactViewViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onEdit: () -> Unit,
    onAddTask: () -> Unit,
    onAddNote: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(ContactViewEvent.ErrorShown)
        }
    }

    state.contact?.let { contact ->
        ContactViewContent(
            contact = contact,
            snackbarHostState = snackbarHostState,
            onBack = onClose,
            onEdit = onEdit,
            onAddTask = onAddTask,
            onAddNote = onAddNote
        )
    } ?: run {
        Text("No contact found", modifier = Modifier.padding(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactViewContent(
    contact: Contact,
    snackbarHostState: SnackbarHostState,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onAddTask: () -> Unit,
    onAddNote: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = contact.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                actions = {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            ContactInfoSection(contact)

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(title = "Tasks", onAddClick = onAddTask, icon = Icons.Default.Add)
            PlaceholderItem("Follow up next week")
            PlaceholderItem("Send coffee machine proposal")

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeader(title = "Notes", onAddClick = onAddNote, icon = Icons.Default.Add)
            PlaceholderItem("Talked about coffee machine. Interested in demo.")
            PlaceholderItem("Mentioned competitor pricing.")

        }
    }
}

@Composable
fun ContactInfoSection(contact: Contact) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        contact.email?.let { Text("📧 $it") }
        contact.phone?.let { Text("📞 $it") }
        contact.company?.let { Text("🏢 $it") }
        contact.notes?.let { Text("📝 ${contact.notes}") }
    }
}

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

@Composable
fun PlaceholderItem(text: String) {
    Text(
        text = "• $text",
        modifier = Modifier.padding(start = 8.dp, top = 4.dp),
        style = MaterialTheme.typography.bodyMedium
    )
}
