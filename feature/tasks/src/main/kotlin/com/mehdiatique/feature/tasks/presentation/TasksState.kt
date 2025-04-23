package com.mehdiatique.feature.tasks.presentation

import com.mehdiatique.core.data.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * Represents the UI state for the Tasks list feature.
 */
data class TasksState(
    val tasks: List<Task> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

fun Task.formattedDueDate(): String? = dueAt?.let {
    SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(it))
}
