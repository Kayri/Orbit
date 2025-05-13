package com.mehdiatique.feature.action.presentation

import com.mehdiatique.core.data.model.Action
import com.mehdiatique.core.data.model.Contact
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 * Represents the UI state for the Actions list feature.
 */
data class ActionsState(
    val actions: List<Action> = emptyList(),
    val owner: Contact? = null,
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

fun Action.formattedDueDate(): String? = dueAt?.let {
    SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date(it))
}
