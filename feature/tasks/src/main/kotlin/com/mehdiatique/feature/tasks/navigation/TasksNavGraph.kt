package com.mehdiatique.feature.tasks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mehdiatique.core.navigation_contract.ContactNav
import com.mehdiatique.core.navigation_contract.TasksNav
import com.mehdiatique.feature.tasks.presentation.TasksScreen
import com.mehdiatique.feature.tasks.presentation.details.TaskDetailScreen

fun NavGraphBuilder.tasksNavGraph(navController: NavController) {
    composable(route = TasksRoute.List.route) {
        TasksScreen(navigateToDetail = { taskId -> navController.navigate(TasksNav.detailRoute(taskId = taskId)) })
    }
    composable(
        route = TasksRoute.Detail.route,
        arguments = listOf(
            navArgument(TasksNav.ARG_TASK_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(TasksNav.ARG_CONTACT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        ),
    ) {
        TaskDetailScreen(
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

sealed class TasksRoute(val route: String) {
    object List : TasksRoute("tasks")
    object Detail : TasksRoute(TasksNav.routePattern())
}