package com.gps_usage.Location

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import com.gps_usage.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.gps_usage.showCoordinates.LocationRepository
import kotlinx.coroutines.isActive
import org.koin.android.ext.android.inject

class LocationService: Service() {

    private var serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    private val binder = LocationBinder()
    private val repository: LocationRepository by inject()

    inner class LocationBinder: Binder() {
        fun getService(): LocationService = this@LocationService
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        if (serviceScope.isActive.not()) {
            serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        }

        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking route...")
            .setContentText("Location: null")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        locationClient
            .getLocationUpdates(2500L)
            .catch {e -> e.printStackTrace() }
            .onEach { location ->
                val latitude = location.latitude.toString()
                val longitude = location.longitude.toString()
                val updatedNotification = notification
                    .setContentText("Location: $latitude, $longitude")
                notificationManager.notify(1, updatedNotification.build())

                repository.updateLocation(location.latitude, location.longitude)
            }
            .launchIn(serviceScope)
        startForeground(1, notification.build())

        Toast.makeText(this, "Location updates started (inside service)", Toast.LENGTH_SHORT).show()
    }

    private fun stop() {
        serviceScope.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()

        Toast.makeText(this, "Location updates stopped (inside service)", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}