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
import androidx.navigation.NavController

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: @Composable () -> Unit
)

@Composable
fun OrbitBottomBar(navController: NavController, currentRoute: String?) {
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

    NavigationBar {
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
