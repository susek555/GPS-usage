package com.gps_usage

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.gps_usage.Location.LocationService
import com.gps_usage.showCoordinates.ShowCoordinates
import com.gps_usage.showCoordinates.data.Coordinates
import com.gps_usage.showCoordinates.data.LocationResponse
import com.gps_usage.showCoordinates.data.isLocationDataSuccessful
import com.gps_usage.ui.theme.GPSusageTheme

class MainActivity : ComponentActivity() {

    private lateinit var locationService: LocationService
    private var isLocationServiceBound: Boolean = false

    //LOCATION SERVICE

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
            val binder = service as LocationService.LocationBinder
            locationService = binder.getService()
            isLocationServiceBound = true
        }

        override fun onServiceDisconnected(className: ComponentName?) {
            isLocationServiceBound = false
        }
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
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        while (!checkPermissions()) {
            requestPermissions()
        }
        enableEdgeToEdge()
        setContent {
            GPSusageTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
//                    StartShowCoordinatesScreen(
//                        context = this,
//                        startService = { intent ->
//                            startServiceHelper(intent)
//                        }
//                    )
                    // locationService.start() or sth to call
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isLocationServiceBound = false
    }
}


@Composable
fun StartShowCoordinatesScreen(
    context: Context,
    startService: (Intent) -> Unit
){
    ShowCoordinates(
        context = context,
        startService = startService
    ).RunApp()
}