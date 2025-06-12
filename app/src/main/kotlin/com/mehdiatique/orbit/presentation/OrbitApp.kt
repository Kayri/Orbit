package com.mehdiatique.orbit.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mehdiatique.core.ui_contract.ScreenUIConfig
import com.mehdiatique.orbit.navigation.OrbitRoute
import com.mehdiatique.orbit.navigation.orbitNavGraph


/**
 * The main UI container for the Orbit app.
 *
 * This composable hosts the global [Scaffold] and [NavHost], providing a consistent layout
 * structure with support for dynamic FAB, top bar, and snackbar per screen via [ScreenUIConfig].
 *
 * The bottom navigation bar is displayed globally and remains visible across all feature screens.
 *
 * UI configuration is injected by feature navigation graphs using the [setConfig] callback,
 * allowing each screen to define its own UI setup in a modular and decoupled way.
 */
@Composable
fun OrbitApp() {
    val navController = rememberNavController()
    val screenConfig = remember { mutableStateOf(ScreenUIConfig()) }

    Scaffold(
        topBar = { screenConfig.value.topBar?.invoke() },
        floatingActionButton = { screenConfig.value.fab?.invoke() },
        snackbarHost = { SnackbarHost(hostState = screenConfig.value.snackbarHostState) },
        bottomBar = { OrbitBottomBar(navController = navController) },
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = OrbitRoute.Contacts.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            orbitNavGraph(
                navController = navController,
                setConfig = { screenConfig.value = it })
        }
    }
}