package com.mehdiatique.feature.contacts.presentation.view

/**
 * Represents user actions in the Contact View screen.
 */
sealed class ContactViewEvent {
    object AddTask : ContactViewEvent()
    object AddNote : ContactViewEvent()
    object EditContact : ContactViewEvent()
    object ErrorShown : ContactViewEvent()
}