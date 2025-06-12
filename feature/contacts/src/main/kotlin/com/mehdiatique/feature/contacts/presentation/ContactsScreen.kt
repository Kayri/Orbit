package com.mehdiatique.feature.contacts.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope.ResizeMode
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.mehdiatique.core.ui_contract.ScreenUIConfig
import com.mehdiatique.orbit.design.components.AnimatedFab
import com.mehdiatique.orbit.design.components.SearchBar
import com.mehdiatique.orbit.design.theme.OrbitTheme
import com.mehdiatique.orbit.design.transition.LocalAnimatedVisibilityScope
import com.mehdiatique.orbit.design.transition.LocalSharedTransitionScope

/**
 * The main screen for displaying and managing contacts.
 *
 * Connects to the ViewModel, handles UI state and events,
 * and delegates rendering to [ContactsScreenContent].
 *
 * @param viewModel The ViewModel providing UI state and events.
 * @param navigateToDetail Called when the user requests to view or edit a contact.
 */
@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel = hiltViewModel(),
    navigateToDetail: (Long?) -> Unit,
    setConfig: (ScreenUIConfig) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state: ContactsState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        setConfig(
            ScreenUIConfig(
                topBar = {
                    SearchBar(
                        query = state.searchQuery,
                        onQueryChange = { viewModel.onEvent(ContactsEvent.SearchQueryChanged(it)) },
                        placeholder = "Search contacts"
                    )
                },
                fab = { AnimatedFab(contentDescription = "Add Contact") { navigateToDetail(null) } },
                snackbarHostState = snackbarHostState
            )
        )
    }

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(ContactsEvent.ErrorShown)
        }
    }

    ContactsScreenContent(
        state = state,
        navigateToDetail = navigateToDetail
    )
}

@Composable
fun ContactsScreenContent(
    state: ContactsState,
    navigateToDetail: (Long?) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(items = state.contacts) { contact ->
            ContactItem(contact = contact, onClick = { navigateToDetail(contact.id) })
        }
    }
}

/**
 * Displays a single contact entry with their name and optional email.
 *
 * @param contact The contact data to display.
 * @param onClick Callback triggered when the item is tapped.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContactItem(contact: Contact, onClick: () -> Unit) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current

    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "contact-${contact.id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    resizeMode = ResizeMode.ScaleToBounds()
                ),
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
}

@Preview(showBackground = true)
@Composable
fun ContactsScreenPreview() {
    OrbitTheme {
        val contact = Contact(id = 1, name = "Ada Lovelace", email = "ada@code.com", createdAt = 0)
        ContactsScreenContent(
            state = ContactsState(
                contacts = listOf(contact, contact, contact)
            )
        )
    }
}
