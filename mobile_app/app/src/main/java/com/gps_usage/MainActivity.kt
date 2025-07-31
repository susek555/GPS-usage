package com.gps_usage

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.gps_usage.Location.LocationService
import com.gps_usage.showCoordinates.MainScreen
import com.gps_usage.showCoordinates.di.apiModule
import com.gps_usage.ui.theme.GPSusageTheme
import com.gps_usage.showCoordinates.di.locationModule
import com.gps_usage.showCoordinates.di.repositoryModule
import com.gps_usage.showCoordinates.navigation.NavigationController
import dagger.hilt.android.AndroidEntryPoint
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var locationService: LocationService
    private var isLocationServiceBound: Boolean = false
    private var isServiceRunning: Boolean = false

    //LOCATION SERVICE

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            val binder = service as LocationService.LocationBinder
            locationService = binder.getService()
            isLocationServiceBound = true

            Toast.makeText(this@MainActivity, "Service is running", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(className: ComponentName?) {
            isLocationServiceBound = false
        }
    }

//    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
//        val manager = getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
//        return manager.getRunningServices(Int.MAX_VALUE).any { it.service.className == serviceClass.name }
//    }

    private fun startLocationService() {
        if (!isServiceRunning) {
            Intent(this, LocationService::class.java).apply {
                action = LocationService.ACTION_START
                startService(this)
            }
            isServiceRunning = true
        }
    }

    private fun stopLocationService() {
        Intent(this, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            startService(this)
        }
        isServiceRunning = false
    }

    //PERMISSIONS RESPONSIBILITY

    private fun checkPermissions(): Boolean {
        return (
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
                )
                &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
    }


    //TODO use it
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
//            101
//        )
//    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.POST_NOTIFICATIONS
            ),
            0
        )
    }

    //MAIN

    override fun onStart() {
        super.onStart()
        Intent(this, LocationService::class.java).also { intent ->
//            startService(intent)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        while (!checkPermissions()) {
            requestPermissions()
        }

        startKoin{
            androidContext(this@MainActivity)
            koin.setProperty("baseUrl", "http://default-ip:8080/")
            modules(locationModule, apiModule, repositoryModule)

            logger(AndroidLogger(Level.DEBUG))
        }

        enableEdgeToEdge()
        setContent {
            GPSusageTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                    NavigationController(
                        startLocationService = { startLocationService() },
                        stopLocationService = { stopLocationService() }
                    )

                    if (isLocationServiceBound) {
                        Toast.makeText(this, "Service is running", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Service is not running", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (isLocationServiceBound) {
            unbindService(connection)
            isLocationServiceBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(locationReceiver)
        stopLocationService()
    }
}


//@Composable
//fun StartShowCoordinatesScreen(
//    startService: () -> Unit,
//    stopService: () -> Unit
//){
//    ShowCoordinatesScreen(
//        startLocationService = startService,
//        stopLocationService = stopService
//    )
//}
