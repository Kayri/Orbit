package com.mehdiatique.feature.tasks.presentation

/**
 * UI events for the Tasks list feature.
 */
sealed class TasksEvent {
    data class SearchQueryChanged(val query: String) : TasksEvent()
    object ErrorShown : TasksEvent()
}
