package com.mehdiatique.feature.action.presentation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope.ResizeMode
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mehdiatique.core.data.model.Action
import com.mehdiatique.orbit.design.components.AnimatedFab
import com.mehdiatique.orbit.design.components.SearchBar
import com.mehdiatique.orbit.design.theme.OrbitTheme
import com.mehdiatique.orbit.design.transition.LocalAnimatedVisibilityScope
import com.mehdiatique.orbit.design.transition.LocalSharedTransitionScope

/**
 * The main screen for displaying and managing actions.
 *
 * Connects to the ViewModel, handles UI state and events,
 * and delegates rendering to [ActionsScreenContent].
 *
 * @param viewModel The ViewModel providing UI state and events.
 * @param navigateToDetail Called when the user requests to view or edit a action.
 */
@Composable
fun ActionsScreen(
    viewModel: ActionsViewModel = hiltViewModel(),
    navigateToDetail: (Long?) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state: ActionsState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(ActionsEvent.ErrorShown)
        }
    }

    ActionsScreenContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onSearchQueryChange = { viewModel.onEvent(ActionsEvent.SearchQueryChanged(it)) },
        navigateToDetail = navigateToDetail,
    )
}

/**
 * Displays the actions UI including search, list, and actions.
 *
 * Stateless and preview-friendly version of the screen.
 *
 * @param state The UI state to render.
 * @param snackbarHostState Snackbar host for transient messages.
 * @param onSearchQueryChange Called when the search query changes.
 * @param navigateToDetail Called when a action is selected or a new one is added.
 */
@Composable
fun ActionsScreenContent(
    state: ActionsState,
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
                placeholder = "Search Actions"
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            AnimatedFab(contentDescription = "Add Actions") { navigateToDetail(null) }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(items = state.actions) { action ->
                ActionItem(action = action, onClick = { navigateToDetail(action.id) })
            }
        }
    }
}

/**
 * Displays a single action entry with their title and content.
 *
 * @param action The action data to display.
 * @param onClick Callback triggered when the item is tapped.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ActionItem(action: Action, onClick: () -> Unit) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current

    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "action-${action.id}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    resizeMode = ResizeMode.ScaleToBounds()
                ),
            onClick = onClick
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PriorityIndicator(priority = 2)

                Spacer(Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = action.title,
                        style = MaterialTheme.typography.titleMedium,
                        textDecoration = if (action.isDone) TextDecoration.LineThrough else null
                    )

                    if (action.dueAt != null) {
                        Text(
                            text = "Due: ${action.formattedDueDate()}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Checkbox or done icon
                if (action.isDone) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Completed",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun PriorityIndicator(priority: Int) {
    val color = when (priority) {
        1 -> Color.Green
        2 -> Color.Yellow
        3 -> Color.Red
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(10.dp)
            .background(color, shape = CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
fun ActionsScreenPreview() {
    OrbitTheme {
        val action = Action(
            id = 1, title = "Action Title",
            isDone = true, createdAt = 0
        )
        ActionsScreenContent(
            state = ActionsState(
                actions = listOf(action, action, action),
            )
        )
    }
}
