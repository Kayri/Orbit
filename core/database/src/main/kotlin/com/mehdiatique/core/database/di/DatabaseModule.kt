package com.mehdiatique.core.database.di

import android.content.Context
import androidx.room.Room
import com.mehdiatique.core.database.OrbitDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides a singleton instance of the OrbitDatabase.
     */
    @Provides
    @Singleton
    fun provideOrbitDatabase(@ApplicationContext context: Context): OrbitDatabase {
        return Room.databaseBuilder(
            context,
            OrbitDatabase::class.java,
            "orbit_database"
        )
            // TODO: (later) Handle automatic migrations.
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides the ContactDao instance from the OrbitDatabase.
     */
    @Provides
    fun provideContactDao(database: OrbitDatabase) = database.contactDao()

    /**
     * Provides the ActionDao instance from the OrbitDatabase.
     */
    @Provides
    fun provideActionDao(database: OrbitDatabase) = database.actionDao()

    /**
     * Provides the InsightDao instance from the OrbitDatabase.
     */
    @Provides
    fun provideInsightDao(database: OrbitDatabase) = database.insightDao()
}