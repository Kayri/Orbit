package com.mehdiatique.feature.contacts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mehdiatique.feature.contacts.presentation.ContactsScreen

fun NavGraphBuilder.contactsNavGraph(navController: NavController) {
    composable(route = ContactsRoute.List.route) {
        ContactsScreen()
    }
}

sealed class ContactsRoute(val route: String) {
    object List : ContactsRoute("contacts")
}