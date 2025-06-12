package com.mehdiatique.feature.insight.presentation

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
import com.mehdiatique.core.data.model.Insight
import com.mehdiatique.core.ui_contract.ScreenUIConfig
import com.mehdiatique.orbit.design.components.OrbitFab
import com.mehdiatique.orbit.design.components.SearchBar
import com.mehdiatique.orbit.design.theme.OrbitTheme

/**
 * The main screen for displaying and managing insights.
 *
 * Connects to the [InsightsViewModel], handles UI state and events,
 * and delegates rendering to [InsightsScreenContent].
 *
 * @param viewModel The ViewModel providing UI state and events.
 * @param navigateToDetail Called when the user requests to view or create an insight.
 * @param setConfig Configures the screen's scaffold (TopBar, FAB, Snackbar, etc.).
 */
@Composable
fun InsightsScreen(
    viewModel: InsightsViewModel = hiltViewModel(),
    navigateToDetail: (Long?) -> Unit,
    setConfig: (ScreenUIConfig) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state: InsightsState by viewModel.state.collectAsStateWithLifecycle()

    val config = remember(state.searchQuery) {
        ScreenUIConfig(
            topBar = {
                SearchBar(
                    query = state.searchQuery,
                    onQueryChange = { viewModel.onEvent(InsightsEvent.SearchQueryChanged(it)) },
                    placeholder = "Search Insights"
                )
            },
            fab = { OrbitFab(contentDescription = "Add Insights") { navigateToDetail(null) } },
            snackbarHostState = snackbarHostState
        )
    }

    LaunchedEffect(config) { setConfig(config) }

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(InsightsEvent.ErrorShown)
        }
    }

    InsightsScreenContent(
        state = state,
        navigateToDetail = navigateToDetail,
    )
}

/**
 * Stateless UI for displaying the list of insights.
 *
 * This composable is preview-friendly and handles only UI rendering.
 *
 * @param state The current UI state from the ViewModel.
 * @param navigateToDetail Called when an insight is selected or when the user wants to add a new one.
 */
@Composable
fun InsightsScreenContent(
    state: InsightsState,
    navigateToDetail: (Long?) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(items = state.insights) { insight ->
            InsightItem(insight = insight, onClick = { navigateToDetail(insight.id) })
        }
    }
}

/**
 * A single item representing an insight in the list.
 *
 * Displays the insight's content and handles click events.
 *
 * @param insight The insight data to render.
 * @param onClick Called when the insight is tapped.
 */
@Composable
fun InsightItem(insight: Insight, onClick: () -> Unit) {
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
                Text(text = insight.content, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InsightsScreenPreview() {
    OrbitTheme {
        val insight = Insight(id = 1, content = "Content of a insight", createdAt = 0)
        InsightsScreenContent(
            state = InsightsState(
                insights = listOf(insight, insight, insight),
            )
        )
    }
}
