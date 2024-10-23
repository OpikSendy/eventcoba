package com.example.eventcoba.data.worker

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class DailyReminderViewModel @Inject constructor() : ViewModel() {

    fun setupDailyReminder(context: Context, isReminderEnabled: Boolean) {
        if (isReminderEnabled) {
            // Schedule periodic work
            val periodicWorkRequest = PeriodicWorkRequestBuilder<DailyReminderWorker>(1, TimeUnit.MINUTES)
                .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
                .build()

//            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//                "daily_reminder_work",
//                ExistingPeriodicWorkPolicy.UPDATE,
//                periodicWorkRequest
//            )

//            val immediateWorkRequest = OneTimeWorkRequestBuilder<DailyReminderWorker>()
//                .build()
//
            WorkManager.getInstance(context).enqueue(
                periodicWorkRequest
            )
            Log.d("DailyReminderViewModel", "Daily reminder scheduled")
        } else {
            WorkManager.getInstance(context).cancelAllWork()
            Log.d("DailyReminderViewModel", "Daily reminder cancelled")
        }
    }

    private fun calculateInitialDelay(): Long {
        val currentTime = System.currentTimeMillis()
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentTime
            set(Calendar.HOUR_OF_DAY, 13)
            set(Calendar.MINUTE, 55)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (calendar.timeInMillis <= currentTime) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return calendar.timeInMillis - currentTime
    }
}