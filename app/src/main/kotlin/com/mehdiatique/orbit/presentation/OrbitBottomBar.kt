package com.mehdiatique.orbit.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

/**
 * A reusable bottom navigation bar for the Orbit app.
 *
 * This composable displays navigation items for core app features like Contacts, Tasks, and Notes.
 * It highlights the selected item based on the current route and supports state restoration when navigating.
 *
 * The bottom bar is intended to be used globally across the app (e.g., in [OrbitApp]), while
 * feature-specific UI such as top bars or FABs are managed by individual screens.
 *
 * @param navController The [NavController] used to perform navigation between routes.
 * @param currentRoute The currently active navigation route, used to highlight the selected item.
 * @param modifier Optional [Modifier] for styling the [NavigationBar].
 */
@Composable
fun OrbitBottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    currentRoute: String?
) {
    val items = listOf(
        BottomNavItem(
            route = "contacts",
            label = "Contacts",
            icon = { Icon(Icons.Filled.Person, contentDescription = "Contacts") }
        ),
        BottomNavItem(
            route = "tasks",
            label = "Tasks",
            icon = { Icon(Icons.Filled.Check, contentDescription = "Tasks") }
        ),
        BottomNavItem(
            route = "notes",
            label = "Notes",
            icon = { Icon(Icons.Filled.Search, contentDescription = "Notes") }
        )
    )

    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = item.icon,
                label = { Text(item.label) }
            )
        }
    }
}

/**
 * Represents a single item in the bottom navigation bar.
 *
 * @param route The navigation route associated with this item.
 * @param label The label text displayed below the icon.
 * @param icon The composable icon shown in the navigation bar.
 */
data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: @Composable () -> Unit
)
