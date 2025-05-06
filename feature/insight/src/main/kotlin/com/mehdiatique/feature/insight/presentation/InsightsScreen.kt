package com.mehdiatique.feature.insight.presentation

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
import com.mehdiatique.core.data.model.Insight
import com.mehdiatique.orbit.design.components.AnimatedFab
import com.mehdiatique.orbit.design.components.SearchBar
import com.mehdiatique.orbit.design.theme.OrbitTheme
import com.mehdiatique.orbit.design.transition.LocalAnimatedVisibilityScope
import com.mehdiatique.orbit.design.transition.LocalSharedTransitionScope

/**
 * The main screen for displaying and managing insights.
 *
 * Connects to the ViewModel, handles UI state and events,
 * and delegates rendering to [InsightsScreenContent].
 *
 * @param viewModel The ViewModel providing UI state and events.
 * @param navigateToDetail Called when the user requests to view or edit a insight.
 */
@Composable
fun InsightsScreen(
    viewModel: InsightsViewModel = hiltViewModel(),
    navigateToDetail: (Long?) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val state: InsightsState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.error) {
        state.error?.let { errorMsg ->
            snackbarHostState.showSnackbar(message = errorMsg)
            viewModel.onEvent(InsightsEvent.ErrorShown)
        }
    }

    InsightsScreenContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onSearchQueryChange = { viewModel.onEvent(InsightsEvent.SearchQueryChanged(it)) },
        navigateToDetail = navigateToDetail,
    )
}

/**
 * Displays the insights UI including search, list, and actions.
 *
 * Stateless and preview-friendly version of the screen.
 *
 * @param state The UI state to render.
 * @param snackbarHostState Snackbar host for transient messages.
 * @param onSearchQueryChange Called when the search query changes.
 * @param navigateToDetail Called when a insight is selected or a new one is added.
 */
@Composable
fun InsightsScreenContent(
    state: InsightsState,
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
                placeholder = "Search Insights"
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            AnimatedFab(contentDescription = "Add Insights") { navigateToDetail(null) }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            items(items = state.insights) { insight ->
                InsightItem(insight = insight, onClick = { navigateToDetail(insight.id) })
            }
        }
    }
}

/**
 * Displays a single insight entry with their title and content.
 *
 * @param insight The insight data to display.
 * @param onClick Callback triggered when the item is tapped.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun InsightItem(insight: Insight, onClick: () -> Unit) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalAnimatedVisibilityScope.current

    with(sharedTransitionScope) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "insight-${insight.id}"),
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
                    Text(text = insight.content, style = MaterialTheme.typography.bodyMedium)
                }
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
