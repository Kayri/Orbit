package com.mehdiatique.core.data.repository

import com.mehdiatique.core.data.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for handling task-related data operations.
 */
interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getTaskById(id: Long): Flow<Task>
    fun getTasksForContact(id: Long): Flow<List<Task>>
    fun searchTasks(query: String): Flow<List<Task>>
    suspend fun addTask(task: Task): Long
    suspend fun updateTask(task: Task)
    suspend fun deleteTaskById(id: Long)
}
