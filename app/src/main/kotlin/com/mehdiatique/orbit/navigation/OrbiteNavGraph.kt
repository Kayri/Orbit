package com.mehdiatique.orbit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.mehdiatique.core.ui_contract.ScreenUIConfig
import com.mehdiatique.feature.action.navigation.ActionRoute
import com.mehdiatique.feature.action.navigation.actionNavGraph
import com.mehdiatique.feature.assistant.navigation.AssistantRoute
import com.mehdiatique.feature.assistant.navigation.assistantNavGraph
import com.mehdiatique.feature.contacts.navigation.ContactsRoute
import com.mehdiatique.feature.contacts.navigation.contactsNavGraph
import com.mehdiatique.feature.insight.navigation.InsightRoute
import com.mehdiatique.feature.insight.navigation.insightNavGraph

fun NavGraphBuilder.orbitNavGraph(
    navController: NavController,
    setConfig: (ScreenUIConfig) -> Unit
) {
    contactsNavGraph(navController = navController, setConfig = setConfig)
    insightNavGraph(navController = navController, setConfig = setConfig)
    actionNavGraph(navController = navController, setConfig = setConfig)
    assistantNavGraph(navController = navController, setConfig = setConfig)
}

/**
 * Defines all top-level navigation routes used in the Orbit application.
 *
 * Each route corresponds to a major feature of the app (Contacts, Actions, Insights).
 * You can extend this sealed class to add new routes or support nested navigation in the future.
 *
 * @property route The unique string identifier used by the navigation system.
 */
sealed class OrbitRoute(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Contacts : OrbitRoute(
        route = ContactsRoute.List.route,
        label = "Contacts",
        icon = Icons.Filled.Person
    )

    object Insights : OrbitRoute(
        route = InsightRoute.List.route,
        label = "Insights",
        icon = Icons.Filled.Search
    )

    object Actions : OrbitRoute(
        route = ActionRoute.List.route,
        label = "Actions",
        icon = Icons.Filled.Check
    )

    object Assistant : OrbitRoute(
        route = AssistantRoute.Chat.route,
        label = "AI",
        icon = Icons.Filled.Build
    )

    companion object {
        val mainRoutes = listOf(Contacts, Actions, Insights, Assistant)
    }
}
