package com.mehdiatique.feature.contacts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mehdiatique.core.navigation_contract.ContactNav
import com.mehdiatique.core.navigation_contract.NotesNav
import com.mehdiatique.feature.contacts.presentation.ContactsScreen
import com.mehdiatique.feature.contacts.presentation.details.ContactDetailScreen

fun NavGraphBuilder.contactsNavGraph(navController: NavController) {
    composable(route = ContactsRoute.List.route) {
        ContactsScreen(navigateToDetail = { contactId -> navController.navigate(ContactNav.detailRoute(contactId = contactId)) })
    }
    composable(
        route = ContactsRoute.Detail.route,
        arguments = listOf(
            navArgument(ContactNav.ARG_CONTACT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        ),
    ) {
        ContactDetailScreen(
            onClose = { navController.popBackStack() },
            onNavigateToAddNote = { contactId -> navController.navigate(NotesNav.detailRoute(contactId = contactId)) },
            onNavigateToNote = { noteId ->
                navController.navigate(NotesNav.detailRoute(noteId = noteId)) {
                    popUpTo(NotesNav.routePattern()) { inclusive = false }
                    launchSingleTop = true
                }
            },
            onNavigateToAddTask = { contactId -> },
            onNavigateToTask = { taskId -> }
        )
    }
}

sealed class ContactsRoute(val route: String) {
    object List : ContactsRoute("contacts")
    object Detail : ContactsRoute(ContactNav.routePattern())
}