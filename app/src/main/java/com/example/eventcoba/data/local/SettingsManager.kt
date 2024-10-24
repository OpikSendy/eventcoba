package com.example.eventcoba.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SettingsManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        @Volatile
        private var INSTANCE: SettingsManager? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingsManager {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingsManager(dataStore)
                INSTANCE = instance
                instance
            }
        }

        val THEME_KEY = booleanPreferencesKey("theme_key")
        val REMINDER_KEY = booleanPreferencesKey("reminder_key")
    }

    // Mendapatkan Flow untuk preferensi tema
    val themeFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
        preferences[THEME_KEY] ?: false // Default ke light mode
    }
        .distinctUntilChanged()
        .flowOn(Dispatchers.IO)

    // Menyimpan pilihan tema
    suspend fun saveTheme(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkMode
        }
    }

    val reminderFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[REMINDER_KEY] ?: false
        }

    // Menyimpan pilihan reminder
    suspend fun saveReminder(isEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[REMINDER_KEY] = isEnabled
        }
    }
}