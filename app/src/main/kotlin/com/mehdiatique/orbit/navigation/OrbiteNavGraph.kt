package com.mehdiatique.orbit.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.orbitNavGraph(snackbarHostState: SnackbarHostState) {
//    composable(route = OrbitRoute.Contacts.route) { ContactsScreen(snackbarHostState = snackbarHostState) }
//    composable(route = OrbitRoute.Notes.route) { NotesScreen(snackbarHostState = snackbarHostState) }
//    composable(route = OrbitRoute.Tasks.route) { TasksScreen(snackbarHostState = snackbarHostState) }
}

sealed class OrbitRoute(val route: String) {
    object Contacts : OrbitRoute("contacts")
    object AddContact : OrbitRoute("contacts/add")

    object Tasks : OrbitRoute("tasks")
    object AddTask : OrbitRoute("tasks/add")

    object Notes : OrbitRoute("notes")
    object AddNote : OrbitRoute("notes/add")
}
