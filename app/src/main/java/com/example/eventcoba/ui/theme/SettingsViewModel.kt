package com.example.eventcoba.ui.theme

import android.app.Activity
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventcoba.data.local.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsManager: SettingsManager
) : ViewModel() {
    // Flow untuk tema
    val themeFlow: Flow<Boolean> = settingsManager.themeFlow

    // Menyimpan preferensi tema
    fun saveTheme(isDarkMode: Boolean, activity: Activity) {
        viewModelScope.launch {
            Log.d("Theme", "Saving theme setting: $isDarkMode")
            settingsManager.saveTheme(isDarkMode)  // Save theme setting to DataStore
            Log.d("Theme", "Theme setting saved, now updating UI")
            updateTheme(isDarkMode, activity)  // Update theme and UI
        }
    }

    // Memperbarui tema dan UI jika diperlukan
    private fun updateTheme(isDarkMode: Boolean, activity: Activity) {
        Log.d("Theme", "Updating theme: $isDarkMode")

        val currentMode = AppCompatDelegate.getDefaultNightMode()
        val newMode = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO

        Log.d("Theme", "Current Mode: $currentMode, New Mode: $newMode")

        // Coba force recreate meskipun mode sama
        AppCompatDelegate.setDefaultNightMode(newMode)
        activity.recreate()
    }



    // Flow untuk reminder
    val reminderFlow: Flow<Boolean> = settingsManager.reminderFlow

    // Menyimpan preferensi reminder
    fun saveReminder(isEnabled: Boolean) {
        viewModelScope.launch {
            settingsManager.saveReminder(isEnabled)
        }
    }
}
