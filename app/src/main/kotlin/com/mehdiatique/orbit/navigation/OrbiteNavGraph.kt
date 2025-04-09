package com.mehdiatique.orbit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.mehdiatique.feature.contacts.navigation.ContactsRoute
import com.mehdiatique.feature.contacts.navigation.contactsNavGraph
import com.mehdiatique.feature.notes.navigation.NotesRoute
import com.mehdiatique.feature.notes.navigation.notesNavGraph
import com.mehdiatique.feature.tasks.navigation.tasksNavGraph

fun NavGraphBuilder.orbitNavGraph(navController: NavController) {
    contactsNavGraph(navController)
    notesNavGraph(navController)
    tasksNavGraph(navController)
}

/**
 * Defines all top-level navigation routes used in the Orbit application.
 *
 * Each route corresponds to a major feature of the app (Contacts, Tasks, Notes).
 * You can extend this sealed class to add new routes or support nested navigation in the future.
 *
 * @property route The unique string identifier used by the navigation system.
 */
sealed class OrbitRoute(val route: String) {
    object Contacts : OrbitRoute(ContactsRoute.List.route)
    object Notes : OrbitRoute(NotesRoute.List.route)
    object Tasks : OrbitRoute("tasks")

    companion object {
        val mainRoutes = listOf(Contacts, Tasks, Notes)
    }
}
