package com.mehdiatique.feature.contacts.presentation.details

import com.mehdiatique.core.data.model.Action
import com.mehdiatique.core.data.model.Contact
import com.mehdiatique.core.data.model.Insight

data class ContactDetails(
    val contact: Contact,
    val actions: List<Action>,
    val insights: List<Insight>
)