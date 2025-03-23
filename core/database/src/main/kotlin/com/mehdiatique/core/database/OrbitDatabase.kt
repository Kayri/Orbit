package com.mehdiatique.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mehdiatique.core.database.dao.NoteDao
import com.mehdiatique.core.database.dao.TaskDao
import com.mehdiatique.core.database.dao.ContactDao
import com.mehdiatique.core.database.entity.NoteEntity
import com.mehdiatique.core.database.entity.TaskEntity
import com.mehdiatique.core.database.entity.ContactEntity

@Database(
    entities = [ContactEntity::class, TaskEntity::class, NoteEntity::class],
    version = 1,
    exportSchema = true
)
abstract class OrbitDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun taskDao(): TaskDao
    abstract fun noteDao(): NoteDao
}