package com.mehdiatique.feature.contacts.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.orbit.design.components.SearchBar
import com.mehdiatique.orbit.design.theme.OrbitTheme

/**
 * A screen that displays and filters a list of contacts using a local scaffold.
 *
 * Includes a search bar in the top app bar and shows errors via a snackbar.
 *
 * @param viewModel Hilt-injected ViewModel managing the contact state.
 */
@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state: ContactsState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(ContactsEvent.ErrorShown)
        }
    }

    Scaffold(
        topBar = {
            SearchBar(
                query = state.searchQuery,
                onQueryChange = { viewModel.onEvent(ContactsEvent.SearchQueryChanged(it)) },
                placeholder = "Search contacts"
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(items = state.filteredContacts) { contact ->
                ContactItem(contact = contact, onClick = { })
            }
        }
    }
}

/**
 * Displays a single contact entry with their name and optional email.
 *
 * @param contact The contact data to display.
 * @param onClick Callback triggered when the item is tapped.
 */
@Composable
fun ContactItem(contact: Contact, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = contact.name, style = MaterialTheme.typography.titleMedium)
                contact.email?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@PreviewFontScale
@PreviewScreenSizes
@Composable
fun ContactsScreenPreview() {
    OrbitTheme {
        ContactsScreen()
    }
}
