package com.mehdiatique.feature.action.presentation.details

import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.core.data.model.Action
import com.mehdiatique.core.data.model.Insight
import com.mehdiatique.feature.action.presentation.details.ActionDetailMode.ADD
import com.mehdiatique.feature.action.presentation.details.ActionDetailMode.EDIT
import com.mehdiatique.feature.action.presentation.details.ActionDetailMode.VIEW

/**
 * Represents the UI state of the Task Detail screen.
 *
 * Holds the current task data, screen mode (add, view, edit),
 * loading status, and any error messages.
 */
data class ActionDetailState(
    val action: Action,
    val mode: ActionDetailMode,
    val owner: Contact? = null,
    val insights: List<Insight> = emptyList(),
    val contacts: List<Contact> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * Enum representing the current mode of the Task Detail screen.
 *
 * - [ADD]: User is creating a new task.
 * - [VIEW]: User is viewing an existing task.
 * - [EDIT]: User is editing an existing task.
 */
enum class ActionDetailMode {
    ADD,
    VIEW,
    EDIT;

    /** Returns true if the screen is in a state where task fields can be edited. */
    fun isEditable() = this == ADD || this == EDIT
}