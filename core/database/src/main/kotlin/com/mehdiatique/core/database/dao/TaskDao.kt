package com.mehdiatique.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
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
    @Query("SELECT * FROM tasks WHERE contactOwnerId = :contactId")
    fun getTasksForContact(contactId: Long): Flow<List<TaskWithRelations>>

    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

}