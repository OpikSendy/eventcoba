package com.example.eventcoba.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.eventcoba.data.repository.EventRepository
import com.example.eventcoba.data.repository.FavoriteRepository
import com.example.eventcoba.data.service.Interface
import com.example.eventcoba.data.service.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide Retrofit Service
    @Provides
    @Singleton
    fun provideApiService(): Interface {
        return Service.instance
    }

    // Provide AppDatabase (Room)
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

    // Provide DAO
    @Provides
    fun provideFavoriteEventDao(db: AppDatabase): FavoriteEventDao {
        return db.favoriteEventDao()
    }

    // Provide EventRepository
    @Provides
    @Singleton
    fun provideEventRepository(apiService: Interface): EventRepository {
        return EventRepository(apiService)
    }

    // Provide FavoriteRepository
    @Provides
    @Singleton
    fun provideFavoriteRepository(favoriteEventDao: FavoriteEventDao): FavoriteRepository {
        return FavoriteRepository(favoriteEventDao)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object DataStoreModule {

        @Provides
        @Singleton
        fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
            return context.dataStore
        }
    }
}