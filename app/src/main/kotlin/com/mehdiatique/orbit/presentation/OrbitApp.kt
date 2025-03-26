package com.mehdiatique.orbit.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mehdiatique.orbit.navigation.OrbitRoute
import com.mehdiatique.orbit.navigation.orbitNavGraph

/**
 * The main UI container for Orbit.
 *
 * This composable hosts the NavHost and overlays a global bottom bar and snackbar host.
 * It does not use a global Scaffold for top bar or FAB – each feature screen (e.g. Contacts)
 * defines its own Scaffold for those elements.
 *
 * The bottom bar is displayed globally and remains consistent across feature screens.
 */
@Composable
fun OrbitApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = remember(currentRoute) {
        OrbitRoute.mainRoutes.any { it.route == currentRoute }
    }

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
                currentRoute = currentRoute
            )
    }
}
