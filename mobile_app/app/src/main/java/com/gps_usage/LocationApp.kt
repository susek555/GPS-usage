package com.gps_usage

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.gps_usage.Location.LocationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LocationApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val channel = NotificationChannel(
            "location",
            "Location",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}