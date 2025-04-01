package com.mehdiatique.core.database.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.mehdiatique.core.database.entity.ContactEntity
import com.mehdiatique.core.database.entity.NoteEntity
import com.mehdiatique.core.database.entity.TaskEntity

data class TaskWithRelations(
    @Embedded val task: TaskEntity,

    @Relation(
        parentColumn = "contactOwnerId",
        entityColumn = "contactId"
    )
    val owner: ContactEntity? = null,

    @Relation(
        parentColumn = "linkedNoteId",
        entityColumn = "noteId"
    )
    val note: NoteEntity? = null
)