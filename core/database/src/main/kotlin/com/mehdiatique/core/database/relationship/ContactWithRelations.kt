package com.mehdiatique.core.database.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.mehdiatique.core.database.entity.ContactEntity
import com.mehdiatique.core.database.entity.InsightEntity
import com.mehdiatique.core.database.entity.ActionEntity

data class ContactWithRelations(
    @Embedded val contact: ContactEntity,

    @Relation(
        parentColumn = "contactId",
        entityColumn = "contactOwnerId"
    )
    val actions: List<ActionEntity> = emptyList(),

    @Relation(
        parentColumn = "contactId",
        entityColumn = "contactOwnerId"
    )
    val insights: List<InsightEntity> = emptyList()
)