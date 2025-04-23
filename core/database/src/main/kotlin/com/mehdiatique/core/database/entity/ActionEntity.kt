package com.mehdiatique.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "actions",
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
data class ActionEntity(
    @PrimaryKey(autoGenerate = true) val actionId: Long = 0,
    val contactOwnerId: Long? = null,
    val title: String,
    val isDone: Boolean = false,
    val priority: Int = 0,
    val dueAt: Long? = null,
    val completedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long? = null
)