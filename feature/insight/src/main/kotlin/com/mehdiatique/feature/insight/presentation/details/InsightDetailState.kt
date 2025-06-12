package com.mehdiatique.feature.insight.presentation.details

import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.core.data.model.Insight
import com.mehdiatique.feature.insight.presentation.details.InsightDetailMode.ADD
import com.mehdiatique.feature.insight.presentation.details.InsightDetailMode.EDIT
import com.mehdiatique.feature.insight.presentation.details.InsightDetailMode.VIEW

/**
 * Represents the UI state of the Insight Detail screen.
 *
 * Holds the current insight data, screen mode (add, view, edit),
 * loading status, and any error messages.
 */
data class InsightDetailState(
    val insight: Insight,
    val mode: InsightDetailMode,
    val contacts: List<Contact> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

/**
 * Enum representing the current mode of the Insight Detail screen.
 *
 * - [ADD]: User is creating a new insight.
 * - [VIEW]: User is viewing an existing insight.
 * - [EDIT]: User is editing an existing insight.
 */
enum class InsightDetailMode {
    ADD,
    VIEW,
    EDIT;

    /** Returns true if the screen is in a state where insight fields can be edited. */
    fun isEditable() = this == ADD || this == EDIT
}