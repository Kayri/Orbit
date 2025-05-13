package com.mehdiatique.orbit.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.mehdiatique.feature.action.navigation.ActionRoute
import com.mehdiatique.feature.action.navigation.actionNavGraph
import com.mehdiatique.feature.contacts.navigation.ContactsRoute
import com.mehdiatique.feature.contacts.navigation.contactsNavGraph
import com.mehdiatique.feature.insight.navigation.InsightRoute
import com.mehdiatique.feature.insight.navigation.insightNavGraph

fun NavGraphBuilder.orbitNavGraph(navController: NavController) {
    contactsNavGraph(navController)
    insightNavGraph(navController)
    actionNavGraph(navController)
}

/**
 * Defines all top-level navigation routes used in the Orbit application.
 *
 * Each route corresponds to a major feature of the app (Contacts, Actions, Insights).
 * You can extend this sealed class to add new routes or support nested navigation in the future.
 *
 * @property route The unique string identifier used by the navigation system.
 */
sealed class OrbitRoute(val route: String) {
    object Contacts : OrbitRoute(ContactsRoute.List.route)
    object Insights : OrbitRoute(InsightRoute.List.route)
    object Actions : OrbitRoute(ActionRoute.List.route)

    companion object {
        val mainRoutes = listOf(Contacts, Actions, Insights)
    }
}
