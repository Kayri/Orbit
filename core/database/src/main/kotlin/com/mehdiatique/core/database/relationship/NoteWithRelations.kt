package com.mehdiatique.core.database.relationship

import androidx.room.Embedded
import androidx.room.Relation
import com.mehdiatique.core.database.entity.ContactEntity
import com.mehdiatique.core.database.entity.NoteEntity
import com.mehdiatique.core.database.entity.TaskEntity

data class NoteWithRelations(
    @Embedded val note: NoteEntity,

    @Relation(
        parentColumn = "contactOwnerId",
        entityColumn = "contactId"
    )
    val owner: ContactEntity? = null,

    @Relation(
        parentColumn = "noteId",
        entityColumn = "linkedNoteId"
    )
    val task: TaskEntity? = null
)