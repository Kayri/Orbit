package com.mehdiatique.core.ui_contract

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable

/**
 * Configuration object used to define screen-level UI elements
 * for the global Scaffold in the Orbit app.
 *
 * This is provided by each screen (inside its navigation entry)
 * and applied by the app-level Scaffold to render the correct FAB,
 * top bar, and Snackbar.
 *
 * @property topBar Optional composable function to display in the top app bar.
 * @property fab Optional composable function for the Floating Action Button.
 * @property snackbarHostState The SnackbarHostState associated with this screen.
 */
data class ScreenUIConfig(
    val topBar: (@Composable () -> Unit)? = null,
    val fab: (@Composable () -> Unit)? = null,
    val snackbarHostState: SnackbarHostState = SnackbarHostState()
)