package com.mehdiatique.core.data.repository

import com.mehdiatique.core.data.mapper.toDomain
import com.mehdiatique.core.data.mapper.toEntity
import com.mehdiatique.core.data.model.Action
import com.mehdiatique.core.database.dao.ActionDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [ActionRepository] that delegates to [ActionDao]
 * for local data persistence using Room.
 */
@Singleton
class ActionRepositoryImpl @Inject constructor(
    private val actionDao: ActionDao
) : ActionRepository {
    override fun getAllActions(): Flow<List<Action>> =
        actionDao.getAllActions().map { entities -> entities.map { it.toDomain() } }

    override fun getActionById(id: Long): Flow<Action> =
        actionDao.getActionById(id = id).map { it.toDomain() }

    override fun getActionsForContact(id: Long): Flow<List<Action>> =
        actionDao.getActionsForContact(contactId = id).map { entities -> entities.map { it.toDomain() } }

    override fun searchActions(query: String): Flow<List<Action>> =
        actionDao.searchActions(query = query).map { entities -> entities.map { it.toDomain() } }

    override suspend fun addAction(action: Action): Long =
        actionDao.insertAction(action = action.toEntity())

    override suspend fun updateAction(action: Action) {
        actionDao.updateAction(action = action.toEntity())
    }

    override suspend fun deleteActionById(id: Long) {
        actionDao.deleteActionById(id = id)
    }
}
