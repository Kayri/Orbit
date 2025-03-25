package com.mehdiatique.orbit.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = OrbitRoute.Contacts.route,
            modifier = Modifier.fillMaxSize()
        ) {
            orbitNavGraph(navController = navController)
        }

        OrbitBottomBar(
            navController = navController,
            currentRoute = currentRoute,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}
