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
    override fun getTasksForContact(id: Long): Flow<List<Task>> =
        taskDao.getTasksForContact(contactId = id).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task = task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task = task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task = task.toEntity())
    }
}