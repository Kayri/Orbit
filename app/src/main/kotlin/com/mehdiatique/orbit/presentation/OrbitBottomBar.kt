package com.mehdiatique.orbit.presentation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mehdiatique.orbit.navigation.OrbitRoute

/**
 * Displays the bottom navigation bar for the Orbit app.
 *
 * This composable renders navigation items for the main routes defined in [OrbitRoute.mainRoutes],
 * allowing users to switch between primary sections of the app such as Contacts, Actions, and Insights.
 *
 * It highlights the currently active route and ensures proper navigation behavior using
 * single-top launch and state restoration to avoid redundant destinations in the back stack.
 *
 * @param navController The [NavController] used to navigate between app destinations.
 * @param modifier Optional [Modifier] for styling the [NavigationBar].
 */
@Composable
fun OrbitBottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(modifier = modifier) {
        OrbitRoute.mainRoutes.forEach { route ->
            NavigationBarItem(
                selected = currentRoute == route.route,
                onClick = {
                    if (currentRoute != route.route) {
                        navController.navigate(route.route) {
                            popUpTo(route.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(route.icon, contentDescription = route.label) },
                label = { Text(route.label) }
            )
        }
    }
}
