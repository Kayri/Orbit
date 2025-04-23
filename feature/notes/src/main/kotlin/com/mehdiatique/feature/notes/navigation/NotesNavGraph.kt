package com.mehdiatique.feature.notes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mehdiatique.core.navigation_contract.ContactNav
import com.mehdiatique.core.navigation_contract.NotesNav
import com.mehdiatique.feature.notes.presentation.NotesScreen
import com.mehdiatique.feature.notes.presentation.details.NoteDetailScreen

fun NavGraphBuilder.notesNavGraph(navController: NavController) {
    composable(route = NotesRoute.List.route) {
        NotesScreen(navigateToDetail = { noteId -> navController.navigate(NotesNav.detailRoute(noteId = noteId)) })
    }
    composable(
        route = NotesRoute.Detail.route,
        arguments = listOf(
            navArgument(NotesNav.ARG_NOTE_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(NotesNav.ARG_CONTACT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        ),
    ) {
        NoteDetailScreen(
            onClose = { navController.popBackStack() },
            onNavigateToContact = { contactId ->
                navController.navigate(ContactNav.detailRoute(contactId = contactId)) {
                    popUpTo(ContactNav.routePattern()) { inclusive = false }
                    launchSingleTop = true
                }
            }
        )
    }
}

sealed class NotesRoute(val route: String) {
    object List : NotesRoute("notes")
    object Detail : NotesRoute(NotesNav.routePattern())
}