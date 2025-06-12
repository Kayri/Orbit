package com.mehdiatique.feature.insight.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mehdiatique.core.navigation_contract.ContactNav
import com.mehdiatique.core.navigation_contract.InsightNav
import com.mehdiatique.core.ui_contract.ScreenUIConfig
import com.mehdiatique.feature.insight.presentation.InsightsScreen
import com.mehdiatique.feature.insight.presentation.details.InsightDetailScreen

fun NavGraphBuilder.insightNavGraph(
    navController: NavController,
    setConfig: (ScreenUIConfig) -> Unit
) {
    composable(route = InsightRoute.List.route) {
        InsightsScreen(
            navigateToDetail = { insightId ->
                navController.navigate(
                    InsightNav.detailRoute(
                        insightId = insightId
                    )
                )
            },
            setConfig = setConfig
        )
    }
    composable(
        route = InsightRoute.Detail.route,
        arguments = listOf(
            navArgument(InsightNav.ARG_INSIGHT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
            navArgument(InsightNav.ARG_CONTACT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        ),
    ) {
        InsightDetailScreen(
            onClose = { navController.popBackStack() },
            onNavigateToContact = { contactId ->
                navController.navigate(ContactNav.detailRoute(contactId = contactId)) {
                    popUpTo(ContactNav.routePattern()) { inclusive = false }
                    launchSingleTop = true
                }
            },
            setConfig = setConfig
        )
    }
}

sealed class InsightRoute(val route: String) {
    object List : InsightRoute("insights")
    object Detail : InsightRoute(InsightNav.routePattern())
}