package com.mehdiatique.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mehdiatique.core.database.entity.TaskEntity
import com.mehdiatique.core.database.relationship.TaskWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Transaction
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskWithRelations>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE taskId = :id")
    fun getTaskById(id: Long): Flow<TaskWithRelations>

    @Transaction
    @Query("SELECT * FROM tasks WHERE contactOwnerId = :contactId")
    fun getTasksForContact(contactId: Long): Flow<List<TaskWithRelations>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    fun searchTasks(query: String): Flow<List<TaskWithRelations>>

    @Insert
    suspend fun insertTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE taskId = :id")
    suspend fun deleteTaskById(id: Long)

}
