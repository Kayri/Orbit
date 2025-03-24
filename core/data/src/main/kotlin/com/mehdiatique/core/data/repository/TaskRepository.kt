package com.mehdiatique.core.data.repository

import com.mehdiatique.core.data.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for handling task-related data operations.
 */
interface TaskRepository {
    fun getTasksForContact(id: Long): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}
