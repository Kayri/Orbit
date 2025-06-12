package com.mehdiatique.core.data.di

import com.mehdiatique.core.data.repository.ContactRepository
import com.mehdiatique.core.data.repository.ContactRepositoryImpl
import com.mehdiatique.core.data.repository.InsightRepository
import com.mehdiatique.core.data.repository.InsightRepositoryImpl
import com.mehdiatique.core.data.repository.ActionRepository
import com.mehdiatique.core.data.repository.ActionRepositoryImpl
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
    abstract fun bindInsightRepository(
        insightRepositoryImpl: InsightRepositoryImpl
    ): InsightRepository

    @Binds
    @Singleton
    abstract fun bindActionRepository(
        actionRepositoryImpl: ActionRepositoryImpl
    ): ActionRepository
}