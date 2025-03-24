package com.mehdiatique.orbit.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mehdiatique.orbit.navigation.OrbitRoute
import com.mehdiatique.orbit.navigation.orbitNavGraph

@Composable
fun OrbitApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            when (currentRoute) {
//                OrbitRoute.Contacts.route -> ContactsTopBar()
//                OrbitRoute.Notes.route -> NotesTopBar()
//                OrbitRoute.Tasks.route -> TasksTopBar()
            }
        },
        bottomBar = {
            OrbitBottomBar(navController = navController, currentRoute = currentRoute)
        },
        floatingActionButton = {
            when (currentRoute) {
                OrbitRoute.Contacts.route -> FloatingActionButton(onClick = {
                    navController.navigate(OrbitRoute.AddContact.route)
                }) { Icon(Icons.Filled.Add, contentDescription = "Add Contact") }
                OrbitRoute.Notes.route -> FloatingActionButton(onClick = {
                    navController.navigate(OrbitRoute.AddNote.route)
                }) { Icon(Icons.Filled.Add, contentDescription = "Add Note") }
                OrbitRoute.Tasks.route -> FloatingActionButton(onClick = {
                    navController.navigate(OrbitRoute.AddTask.route)
                }) { Icon(Icons.Filled.Add, contentDescription = "Add Task") }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = OrbitRoute.Contacts.route,
            modifier = Modifier.padding(padding)
        ) {
            orbitNavGraph(snackbarHostState = snackbarHostState)
        }
    }
}
