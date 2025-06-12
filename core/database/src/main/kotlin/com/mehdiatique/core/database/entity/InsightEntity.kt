package com.mehdiatique.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "insights",
    foreignKeys = [
        ForeignKey(
            entity = ContactEntity::class,
            parentColumns = ["contactId"],
            childColumns = ["contactOwnerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ActionEntity::class,
            parentColumns = ["actionId"],
            childColumns = ["actionOwnerId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("contactOwnerId"), Index("actionOwnerId")]
)
data class InsightEntity(
    @PrimaryKey(autoGenerate = true) val insightId: Long = 0,
    val content: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long? = null,
    val contactOwnerId: Long? = null,
    val actionOwnerId: Long? = null
)