package com.mehdiatique.feature.contacts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mehdiatique.core.navigation_contract.NotesNav
import com.mehdiatique.feature.contacts.presentation.ContactsScreen
import com.mehdiatique.feature.contacts.presentation.details.ContactDetailScreen

fun NavGraphBuilder.contactsNavGraph(navController: NavController) {
    composable(route = ContactsRoute.List.route) {
        ContactsScreen(
            navigateToDetail = { contactId -> navController.navigate(ContactsRoute.Detail.navigateTo(contactId)) })
    }
    composable(
        route = ContactsRoute.Detail.route,
        arguments = listOf(
            navArgument(ContactsRoute.Detail.ARG_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        ),
    ) {
        ContactDetailScreen(
            onClose = { navController.popBackStack() },
            onNavigateToAddNote = { contactId -> navController.navigate(NotesNav.detailRoute(contactId = contactId)) },
            onNavigateToNote = { noteId -> navController.navigate(NotesNav.detailRoute(noteId = noteId))},
            onNavigateToAddTask = { contactId -> },
            onNavigateToTask = { taskId -> }
        )
    }
}

sealed class ContactsRoute(val route: String) {
    object List : ContactsRoute("contacts")

    object Detail : ContactsRoute("contact_detail?id={id}") {
        const val ARG_ID = "id"
        fun navigateTo(id: Long?) = if (id != null) "contact_detail?$ARG_ID=$id" else "contact_detail"
    }
}