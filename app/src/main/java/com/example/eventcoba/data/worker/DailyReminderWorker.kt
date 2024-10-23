package com.example.eventcoba.data.worker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.eventcoba.MainActivity
import com.example.eventcoba.R
import com.example.eventcoba.data.local.SettingsManager
import com.example.eventcoba.data.local.dataStore
import com.example.eventcoba.data.model.ListEventsItem
import com.example.eventcoba.data.service.Service
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class DailyReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("DailyReminderWorker", "Worker started")
        return try {
            val settingsManager = SettingsManager.getInstance(applicationContext.dataStore)

            // Check if the reminder is still enabled
            val isReminderEnabled = settingsManager.reminderFlow.first()

            if (!isReminderEnabled) {
                Log.d("DailyReminderWorker", "Reminder is disabled, cancelling worker.")
                WorkManager.getInstance(applicationContext).cancelUniqueWork("daily_reminder_work")
                return Result.success()
            }

            // Fetch events
            val response = Service.instance.getNearestActiveEvent()
            if (response.isSuccessful) {
                val event = response.body()?.listEvents?.firstOrNull()
                event?.let {
                    Log.d("DailyReminderWorker", "Displaying notification for event: ${it.name}")
                    showNotification(it)
                }
                Result.success()
            } else {
                Log.e("DailyReminderWorker", "Failed to fetch event, response code: ${response.code()}")
                Result.retry() // Pertimbangkan untuk retry jika gagal
            }
        } catch (e: Exception) {
            Log.e("DailyReminderWorker", "Error: ${e.message}")
            Result.failure()
        }
    }

    // Fungsi untuk menampilkan notifikasi
    @SuppressLint("ObsoleteSdkInt")
    private fun showNotification(event: ListEventsItem) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "daily_reminder_channel",
                "Daily Reminder",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val bundle = Bundle().apply {
            putParcelable("event", event)
        }

        val pendingIntent: PendingIntent = NavDeepLinkBuilder(applicationContext)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.detailFragment)
            .setArguments(bundle)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(applicationContext, "daily_reminder_channel")
            .setSmallIcon(R.drawable.ic_event)
            .setContentTitle("Event Terdekat")
            .setContentText("Event: ${event.name} pada ${event.beginTime}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
        Log.d("DailyReminderWorker", "Notification displayed")
    }
}

