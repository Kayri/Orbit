package com.mehdiatique.feature.notes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mehdiatique.feature.notes.presentation.NotesScreen
import com.mehdiatique.feature.notes.presentation.details.NoteDetailScreen

fun NavGraphBuilder.notesNavGraph(navController: NavController) {
    composable(route = NotesRoute.List.route) {
        NotesScreen(
            navigateToDetail = { contactId -> navController.navigate(NotesRoute.Detail.navigateTo(contactId)) }
        )
    }
    composable(
        route = NotesRoute.Detail.route,
        arguments = listOf(
            navArgument(NotesRoute.Detail.ARG_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        ),
    ) {
        NoteDetailScreen(
            onClose = { navController.popBackStack() },
        )
    }
}

sealed class NotesRoute(val route: String) {
    object List : NotesRoute("notes")

    object Detail : NotesRoute("note_detail?id={id}") {
        const val ARG_ID = "id"
        fun navigateTo(id: Long?) = if (id != null) "note_detail?$ARG_ID=$id" else "note_detail"
    }
}