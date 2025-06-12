package com.mehdiatique.orbit.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mehdiatique.core.ui_contract.ScreenUIConfig
import com.mehdiatique.orbit.design.transition.LocalAnimatedVisibilityScope
import com.mehdiatique.orbit.design.transition.LocalSharedTransitionScope
import com.mehdiatique.orbit.navigation.OrbitRoute
import com.mehdiatique.orbit.navigation.orbitNavGraph

/**
 * The main UI container for the Orbit app.
 *
 * This composable hosts the global [Scaffold] and [NavHost], providing a consistent layout
 * structure with support for dynamic FAB, top bar, and snackbar per screen via [ScreenUIConfig].
 *
 * It also wraps the navigation graph in a [SharedTransitionLayout], enabling shared element
 * transitions between screens that opt into them.
 *
 * The bottom navigation bar is displayed globally and remains visible across all feature screens.
 *
 * UI configuration is injected by feature navigation graphs using the [setConfig] callback,
 * ensuring a modular and decoupled architecture.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun OrbitApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val screenConfig = remember { mutableStateOf(ScreenUIConfig()) }

    SharedTransitionLayout {
        CompositionLocalProvider(LocalSharedTransitionScope provides this) {
            AnimatedContent(
                targetState = currentBackStackEntry,
                label = "AnimatedNavHost"
            ) { _ ->
                CompositionLocalProvider(LocalAnimatedVisibilityScope provides this) {
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
                                .padding(innerPadding)
                        ) {
                            orbitNavGraph(
                                navController = navController,
                                setConfig = { screenConfig.value = it })
                        }
                    }
                }
            }
        }
    }
}