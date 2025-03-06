package com.gps_usage

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.gps_usage.showCoordinates.ShowCoordinates
import com.gps_usage.showCoordinates.data.Coordinates
import com.gps_usage.showCoordinates.data.LocationResponse
import com.gps_usage.showCoordinates.data.isLocationDataSuccessful
import com.gps_usage.ui.theme.GPSusageTheme

class MainActivity : ComponentActivity() {
//    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        while (!checkPermissions()) {
            requestPermissions()
        }
        enableEdgeToEdge()
        setContent {
            GPSusageTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
                    StartShowCoordinatesScreen { getCurrentLocation() }
                }
            }
        }
    }

    // GPS RESPONSIBILITY

//    private var latitude: Double = 0.0
//    private var longitude: Double = 0.0
//    private var response: isLocationDataSuccessful = isLocationDataSuccessful.UNKNOWN_ERROR
//
//    private fun getCurrentLocation(): LocationResponse {
//        if(checkPermissions()) {
//            if(isLocationEnabled()) {
//
//                if (ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION
//                    ) != PackageManager.PERMISSION_GRANTED
//                ) {
//                    response = isLocationDataSuccessful.NO_PERMISSIONS
//                }
//                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){ task ->
//                    val location: Location?=task.result
//                    if(location == null){
//                        Toast.makeText(this, "Null received", Toast.LENGTH_SHORT).show()
//                        response = isLocationDataSuccessful.UNKNOWN_ERROR
//                    } else {
//                        Toast.makeText(this, "Get successful", Toast.LENGTH_SHORT).show()
//                        latitude = location.latitude
//                        longitude = location.longitude
//                        response = isLocationDataSuccessful.SUCCESS
//                    }
//
//                }
//            } else {
//                Toast.makeText(this, "Turn on location", Toast.LENGTH_SHORT).show()
//                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
//                response =  isLocationDataSuccessful.LOCATION_OFF
//            }
//        } else {
//            requestPermissions()
//            response = isLocationDataSuccessful.REQUESTED_FOR_PERMISSIONS
//        }
//
//        return LocationResponse(
//            coordinates = Coordinates(latitude, longitude),
//            response = isLocationDataSuccessful.SUCCESS
//        )
//    }

//    companion object {
//        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
//    }

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
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            0
        )
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray,
//        deviceId: Int
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
//
//        if(requestCode == PERMISSION_REQUEST_ACCESS_LOCATION)
//        {
//            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT).show()
////                getCurrentLocation()
//            } else {
//                Toast.makeText(applicationContext, "Denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

//    private fun isLocationEnabled(): Boolean {
//        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return (
//                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                        ||
//                        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//                )
//    }
}


@Composable
fun StartShowCoordinatesScreen(getLocation: () -> LocationResponse){
    ShowCoordinates(getLocation).RunApp()
}