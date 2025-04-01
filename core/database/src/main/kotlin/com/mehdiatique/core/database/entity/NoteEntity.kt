package com.mehdiatique.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = ContactEntity::class,
            parentColumns = ["contactId"],
            childColumns = ["contactOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("contactOwnerId")]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val noteId: Long = 0,
    val content: String,
    val title: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long? = null,
    val contactOwnerId: Long? = null // Optional link
)