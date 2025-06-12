package com.mehdiatique.feature.contacts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mehdiatique.core.navigation_contract.ContactNav
import com.mehdiatique.core.navigation_contract.InsightNav
import com.mehdiatique.core.ui_contract.ScreenUIConfig
import com.mehdiatique.feature.contacts.presentation.ContactsScreen
import com.mehdiatique.feature.contacts.presentation.details.ContactDetailScreen

fun NavGraphBuilder.contactsNavGraph(
    navController: NavController,
    setConfig: (ScreenUIConfig) -> Unit
) {
    composable(route = ContactsRoute.List.route) {
        ContactsScreen(
            navigateToDetail = { contactId ->
                navController.navigate(
                    route = ContactNav.detailRoute(
                        contactId = contactId
                    )
                )
            },
            setConfig = setConfig
        )
    }
    composable(
        route = ContactsRoute.Detail.route,
        arguments = listOf(
            navArgument(name = ContactNav.ARG_CONTACT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        ),
    ) {
        ContactDetailScreen(
            onClose = { navController.popBackStack() },
            onNavigateToAddAction = { contactId -> },
            onNavigateToAction = { actionId -> },
            onNavigateToAddInsight = { contactId ->
                navController.navigate(
                    InsightNav.detailRoute(
                        contactId = contactId
                    )
                )
            },
            onNavigateToInsight = { insightId ->
                navController.navigate(InsightNav.detailRoute(insightId = insightId)) {
                    popUpTo(InsightNav.routePattern()) { inclusive = false }
                    launchSingleTop = true
                }
            },
            setConfig = setConfig
        )
    }
}

sealed class ContactsRoute(val route: String) {
    object List : ContactsRoute("contacts")
    object Detail : ContactsRoute(ContactNav.routePattern())
}