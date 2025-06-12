package com.mehdiatique.core.database.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.mehdiatique.core.database.entity.ContactEntity
import com.mehdiatique.core.database.entity.InsightEntity
import com.mehdiatique.core.database.entity.ActionEntity

data class ActionWithInsights(
    @Embedded val action: ActionEntity,

    @Relation(
        parentColumn = "actionId",
        entityColumn = "actionOwnerId"
    )
    val insights: List<InsightEntity> = emptyList()
)
