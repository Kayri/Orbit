package com.mehdiatique.orbit.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mehdiatique.orbit.design.transition.LocalAnimatedVisibilityScope
import com.mehdiatique.orbit.design.transition.LocalSharedTransitionScope
import com.mehdiatique.orbit.navigation.OrbitRoute
import com.mehdiatique.orbit.navigation.orbitNavGraph

/**
 * The main UI container for Orbit.
 *
 * This composable hosts the NavHost inside a SharedTransitionLayout + AnimatedContent wrapper,
 * providing transition scopes via CompositionLocals for shared element animations.
 *
 * The bottom bar is displayed globally and remains consistent across feature screens.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun OrbitApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    SharedTransitionLayout {
        CompositionLocalProvider(LocalSharedTransitionScope provides this) {
            AnimatedContent(
                targetState = currentBackStackEntry,
                label = "NavigationContent"
            ) { backStackEntry ->
                CompositionLocalProvider(LocalAnimatedVisibilityScope provides this) {
                    val route = backStackEntry?.destination?.route
                    val showBottomBar = OrbitRoute.mainRoutes.any { it.route == route }
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = OrbitRoute.Contacts.route,
                            modifier = Modifier.weight(1f)
                        ) {
                            orbitNavGraph(navController = navController)
                        }
                        if (showBottomBar)
                            OrbitBottomBar(
                                navController = navController,
                                currentRoute = route
                            )
                    }
                }
            }
        }
    }
}

