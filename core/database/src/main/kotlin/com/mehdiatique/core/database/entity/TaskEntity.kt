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
        )
    ],
    indices = [Index("contactOwnerId")]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val taskId: Long = 0,
    val contactOwnerId: Long? = null, // Optional link to a contact
    val title: String,
    val description: String? = null,
    val isDone: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long? = null,
    val dueAt: Long? = null,       // Deadline or reminder date
    val completedAt: Long? = null, // Set when task is marked done
    val priority: Int = 0          // 0=none, 1=low, 2=med, 3=high (or use enum)
)