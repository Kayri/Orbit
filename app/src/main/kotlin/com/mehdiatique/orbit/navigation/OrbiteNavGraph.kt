package com.mehdiatique.orbit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

fun NavGraphBuilder.orbitNavGraph(navController: NavController) {
//    contactsNavGraph(navController)
//    tasksNavGraph(navController)
//    notesNavGraph(navController)
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
    object Contacts : OrbitRoute("contacts")
    object Tasks : OrbitRoute("tasks")
    object Notes : OrbitRoute("notes")
}
