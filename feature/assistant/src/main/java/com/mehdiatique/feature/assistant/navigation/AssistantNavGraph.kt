package com.mehdiatique.feature.assistant.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mehdiatique.core.ui_contract.ScreenUIConfig
import com.mehdiatique.feature.assistant.presentation.AssistantScreen

fun NavGraphBuilder.assistantNavGraph(
    navController: NavController,
    setConfig: (ScreenUIConfig) -> Unit
) {
    composable(route = AssistantRoute.Chat.route) {
        AssistantScreen(
            setConfig = setConfig
        )
    }
}

sealed class AssistantRoute(val route: String) {
    object Chat : AssistantRoute("assistant")
}