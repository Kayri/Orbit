package com.mehdiatique.feature.action.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mehdiatique.core.navigation_contract.ContactNav
import com.mehdiatique.core.navigation_contract.InsightNav
import com.mehdiatique.core.navigation_contract.ActionNav
import com.mehdiatique.core.ui_contract.ScreenUIConfig
import com.mehdiatique.feature.action.presentation.ActionsScreen
import com.mehdiatique.feature.action.presentation.details.ActionDetailScreen

fun NavGraphBuilder.actionNavGraph(
    navController: NavController,
    setConfig: (ScreenUIConfig) -> Unit
) {
    composable(route = ActionRoute.List.route) {
        ActionsScreen(
            navigateToDetail = { actionId ->
                navController.navigate(
                    ActionNav.detailRoute(
                        actionId = actionId
                    )
                )
            },
            setConfig = setConfig
        )
    }
    composable(
        route = ActionRoute.Detail.route,
        arguments = listOf(
            navArgument(ActionNav.ARG_ACTION_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(ActionNav.ARG_CONTACT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        ),
    ) {
        ActionDetailScreen(
            onClose = { navController.popBackStack() },
            onNavigateToContact = { contactId ->
                navController.navigate(ContactNav.detailRoute(contactId = contactId)) {
                    popUpTo(ContactNav.routePattern()) { inclusive = false }
                    launchSingleTop = true
                }
            },
            onNavigateToAddInsight = {
            },
            onNavigateToInsight = { insightId ->
                navController.navigate(InsightNav.detailRoute(insightId = insightId)) {
                    popUpTo(ContactNav.routePattern()) { inclusive = false }
                    launchSingleTop = true
                }
            },
            setConfig = setConfig
        )
    }
}

sealed class ActionRoute(val route: String) {
    object List : ActionRoute("actions")
    object Detail : ActionRoute(ActionNav.routePattern())
}