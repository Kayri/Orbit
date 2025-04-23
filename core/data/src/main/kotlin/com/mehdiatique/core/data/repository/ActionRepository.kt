package com.mehdiatique.core.data.repository

import com.mehdiatique.core.data.model.Action
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for handling action-related data operations.
 */
interface ActionRepository {
    fun getAllActions(): Flow<List<Action>>
    fun getActionById(id: Long): Flow<Action>
    fun getActionsForContact(id: Long): Flow<List<Action>>
    fun searchActions(query: String): Flow<List<Action>>
    suspend fun addAction(action: Action): Long
    suspend fun updateAction(action: Action)
    suspend fun deleteActionById(id: Long)
}
