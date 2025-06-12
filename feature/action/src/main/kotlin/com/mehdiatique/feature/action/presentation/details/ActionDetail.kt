package com.mehdiatique.feature.action.presentation.details

import com.mehdiatique.core.data.model.Action
import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.core.data.model.Insight

data class ActionDetails(
    val action: Action,
    val owner: Contact?,
    val insights: List<Insight>
)