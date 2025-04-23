package com.mehdiatique.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = ContactEntity::class,
            parentColumns = ["contactId"],
            childColumns = ["contactOwnerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = NoteEntity::class,
            parentColumns = ["noteId"],
            childColumns = ["linkedNoteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("contactOwnerId"), Index("linkedNoteId")]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val taskId: Long = 0,
    val title: String,
    val content: String? = null,
    val isDone: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long? = null,
    val dueAt: Long? = null,       // Deadline or reminder date
    val completedAt: Long? = null, // Set when task is marked done
    val priority: Int = 0,          // 0=none, 1=low, 2=med, 3=high (or use enum)
    val contactOwnerId: Long? = null, // Optional link
    val linkedNoteId: Long? = null // Optional 1-to-1 link
)