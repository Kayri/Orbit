package com.mehdiatique.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true) val contactId: Long = 0,
    val name: String,
    val email: String? = null,
    val phone: String? = null,
    val company: String? = null,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)