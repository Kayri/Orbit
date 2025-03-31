package com.mehdiatique.core.database.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.mehdiatique.core.database.entity.ContactEntity
import com.mehdiatique.core.database.entity.NoteEntity
import com.mehdiatique.core.database.entity.TaskEntity

data class ContactWithRelations(
    @Embedded val contact: ContactEntity,

    @Relation(
        parentColumn = "contactId",
        entityColumn = "contactOwnerId"
    )
    val notes: List<NoteEntity> = emptyList(),

    @Relation(
        parentColumn = "contactId",
        entityColumn = "contactOwnerId"
    )
    val tasks: List<TaskEntity> = emptyList()
)