package com.mehdiatique.core.data.repository

import com.mehdiatique.core.data.mapper.toDomain
import com.mehdiatique.core.data.mapper.toEntity
import com.mehdiatique.core.data.model.Task
import com.mehdiatique.core.database.dao.TaskDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [TaskRepository] that delegates to [TaskDao]
 * for local data persistence using Room.
 */
@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {
    override fun getAllTasks(): Flow<List<Task>> =
        taskDao.getAllTasks().map { entities ->
            entities.map { it.toDomain() }
        }

    override fun getTaskById(id: Long): Flow<Task> =
        taskDao.getTaskById(id = id).map { it.toDomain() }

    override fun getTasksForContact(id: Long): Flow<List<Task>> =
        taskDao.getTasksForContact(contactId = id).map { entities ->
            entities.map { it.toDomain() }
        }

    override fun searchTasks(query: String): Flow<List<Task>> =
        taskDao.searchTasks(query = query).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun addTask(task: Task): Long =
        taskDao.insertTask(task = task.toEntity())

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task = task.toEntity())
    }

    override suspend fun deleteTaskById(id: Long) {
        taskDao.deleteTaskById(id = id)
    }
}
