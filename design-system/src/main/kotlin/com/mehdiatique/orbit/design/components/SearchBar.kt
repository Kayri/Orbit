package com.mehdiatique.orbit.design.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A reusable top app bar containing a search field.
 *
 * This composable is stateless and delegates all state and event handling to its caller,
 * making it suitable for reuse across different features (e.g., contacts, notes, tasks).
 *
 * @param modifier Optional [Modifier] for customizing layout behavior (e.g., padding, width).
 * @param placeholder Optional placeholder text displayed inside the search field when empty.
 * @param query The current value of the search input.
 * @param onQueryChange Callback invoked when the user types or changes the input.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    placeholder: String?,
    query: String,
    onQueryChange: (String) -> Unit
) {
    TopAppBar(
        title = {
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = { placeholder?.let { Text(it) } },
                singleLine = true,
                modifier = modifier.fillMaxWidth()
            )
        }
    )
}