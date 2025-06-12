package com.mehdiatique.feature.action.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.mehdiatique.core.ui_contract.ScreenUIConfig
import com.mehdiatique.orbit.design.components.OrbitFab
import com.mehdiatique.orbit.design.components.SearchBar
import com.mehdiatique.orbit.design.theme.OrbitTheme

/**
 * Main screen for displaying and managing actions.
 *
 * Collects state from [ActionsViewModel], sets up the scaffold configuration
 * (top bar, FAB, snackbar), and delegates UI rendering to [ActionsScreenContent].
 *
 * @param viewModel The Hilt-injected ViewModel providing state and handling events.
 * @param navigateToDetail Callback for navigating to the action detail screen. Pass `null` to create a new action.
 * @param setConfig Callback to configure the screen’s scaffold (top bar, FAB, snackbar, etc.).
 */
@Composable
fun ActionsScreen(
    viewModel: ActionsViewModel = hiltViewModel(),
    navigateToDetail: (Long?) -> Unit,
    setConfig: (ScreenUIConfig) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state: ActionsState by viewModel.state.collectAsStateWithLifecycle()

    val config = remember(state.searchQuery) {
        ScreenUIConfig(
            topBar = {
                SearchBar(
                    query = state.searchQuery,
                    onQueryChange = { viewModel.onEvent(ActionsEvent.SearchQueryChanged(it)) },
                    placeholder = "Search Actions"
                )
            },
            fab = { OrbitFab(contentDescription = "Add Actions") { navigateToDetail(null) } },
            snackbarHostState = snackbarHostState
        )
    }

    LaunchedEffect(config) { setConfig(config) }

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(ActionsEvent.ErrorShown)
        }
    }

    ActionsScreenContent(
        state = state,
        navigateToDetail = navigateToDetail
    )
}

/**
 * Stateless UI for rendering the list of actions.
 *
 * This composable displays a scrollable list of [ActionItem] and delegates navigation logic
 * via the [navigateToDetail] callback.
 *
 * @param state The current UI state containing the list of actions.
 * @param navigateToDetail Callback triggered when an action is selected or a new one is to be created.
 */
@Composable
fun ActionsScreenContent(
    state: ActionsState,
    navigateToDetail: (Long?) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(items = state.actions) { action ->
            ActionItem(action = action, onClick = { navigateToDetail(action.id) })
        }
    }
}

/**
 * Displays a single action item in a card layout.
 *
 * Shows the action title, optional due date, completion status, and a visual indicator
 * of priority.
 *
 * @param action The [Action] data to render.
 * @param onClick Callback triggered when the card is tapped.
 */
@Composable
fun ActionItem(action: Action, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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

            Column(modifier = Modifier.weight(1f)) {
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
