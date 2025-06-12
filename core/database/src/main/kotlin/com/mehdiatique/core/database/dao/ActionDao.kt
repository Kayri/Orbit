package com.mehdiatique.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mehdiatique.core.database.entity.ActionEntity
import com.mehdiatique.core.database.relationship.ActionWithInsights
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionDao {
    @Transaction
    @Query("SELECT * FROM actions")
    fun getAllActions(): Flow<List<ActionWithInsights>>

    @Transaction
    @Query("SELECT * FROM actions WHERE actionId = :id")
    fun getActionById(id: Long): Flow<ActionWithInsights>

    @Transaction
    @Query("SELECT * FROM actions WHERE contactOwnerId = :contactId")
    fun getActionsForContact(contactId: Long): Flow<List<ActionWithInsights>>

    @Transaction
    @Query("SELECT * FROM actions WHERE title LIKE '%' || :query || '%'")
    fun searchActions(query: String): Flow<List<ActionWithInsights>>

    @Insert
    suspend fun insertAction(action: ActionEntity): Long

    @Update
    suspend fun updateAction(action: ActionEntity)

    @Query("DELETE FROM actions WHERE actionId = :id")
    suspend fun deleteActionById(id: Long)
}