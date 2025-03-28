package com.mehdiatique.feature.notes.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope.ResizeMode
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
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
import com.mehdiatique.core.data.model.Note
import com.mehdiatique.orbit.design.components.AnimatedFab
import com.mehdiatique.orbit.design.components.SearchBar
import com.mehdiatique.orbit.design.theme.OrbitTheme
import com.mehdiatique.orbit.design.transition.LocalAnimatedVisibilityScope
import com.mehdiatique.orbit.design.transition.LocalSharedTransitionScope

/**
 * The main screen for displaying and managing notes.
 *
 * Connects to the ViewModel, handles UI state and events,
 * and delegates rendering to [NotesScreenContent].
 *
 * @param viewModel The ViewModel providing UI state and events.
 * @param navigateToDetail Called when the user requests to view or edit a note.
 */
@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    navigateToDetail: (Long?) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state: NotesState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(NotesEvent.ErrorShown)
        }
    }

    NotesScreenContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onSearchQueryChange = { viewModel.onEvent(NotesEvent.SearchQueryChanged(it)) },
        navigateToDetail = navigateToDetail,
    )
}

/**
 * Displays the notes UI including search, list, and actions.
 *
 * Stateless and preview-friendly version of the screen.
 *
 * @param state The UI state to render.
 * @param snackbarHostState Snackbar host for transient messages.
 * @param onSearchQueryChange Called when the search query changes.
 * @param navigateToDetail Called when a note is selected or a new one is added.
 */
@Composable
fun NotesScreenContent(
    state: NotesState,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onSearchQueryChange: (String) -> Unit = {},
    navigateToDetail: (Long?) -> Unit = {},
) {
    Scaffold(
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.exclude(NavigationBarDefaults.windowInsets),
        topBar = {
            SearchBar(
                query = state.searchQuery,
                onQueryChange = onSearchQueryChange,
                placeholder = "Search Notes"
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            AnimatedFab(contentDescription = "Add Notes") { navigateToDetail(null) }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(items = state.notes) { note ->
                NoteItem(note = note, onClick = { navigateToDetail(note.id) })
            }
        }
    }
}

/**
 * Displays a single note entry with their title and content.
 *
 * @param note The note data to display.
 * @param onClick Callback triggered when the item is tapped.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NoteItem(note: Note, onClick: () -> Unit) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current

    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "note-${note.id}"),
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
                    Text(text = note.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotesScreenPreview() {
    OrbitTheme {
        val note = Note(id = 1, content = "Content of a note", title = "Note Title", null, 0, null)
        NotesScreenContent(
            state = NotesState(
                notes = listOf(note, note, note),
            )
        )
    }
}
