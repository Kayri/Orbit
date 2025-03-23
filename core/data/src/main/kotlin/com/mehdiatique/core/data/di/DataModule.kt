package com.mehdiatique.core.data.di

import com.mehdiatique.core.data.repository.ContactRepository
import com.mehdiatique.core.data.repository.ContactRepositoryImpl
import com.mehdiatique.core.data.repository.NoteRepository
import com.mehdiatique.core.data.repository.NoteRepositoryImpl
import com.mehdiatique.core.data.repository.TaskRepository
import com.mehdiatique.core.data.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that binds repository interfaces to their implementations.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindContactRepository(
        contactRepositoryImpl: ContactRepositoryImpl
    ): ContactRepository

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        noteRepositoryImpl: NoteRepositoryImpl
    ): NoteRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository
}