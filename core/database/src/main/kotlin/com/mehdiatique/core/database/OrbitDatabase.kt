package com.mehdiatique.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mehdiatique.core.database.dao.InsightDao
import com.mehdiatique.core.database.dao.ActionDao
import com.mehdiatique.core.database.dao.ContactDao
import com.mehdiatique.core.database.entity.InsightEntity
import com.mehdiatique.core.database.entity.ActionEntity
import com.mehdiatique.core.database.entity.ContactEntity

@Database(
    entities = [ContactEntity::class, ActionEntity::class, InsightEntity::class],
    version = 1,
    exportSchema = true
)
abstract class OrbitDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun actionDao(): ActionDao
    abstract fun insightDao(): InsightDao
}