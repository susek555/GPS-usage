package com.gps_usage.showCoordinates

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.provider.Settings

class ShowCoordinatesActivity: AppCompatActivity() {
    var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    var latitude: Float = 0f
    var longitude: Float = 0f

    fun getCurrentLocation(): isLocationDateSuccessful {
        if(checkPermissions()) {
            if(isLocationEnabled()) {
                //TODO get location
                return isLocationDateSuccessful.SUCCESS
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_SHORT).show()
                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
                return isLocationDateSuccessful.LOCATION_OFF
            }
        } else {
            requestPermissions()
            return isLocationDateSuccessful.SUCCESS
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
    }

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
//            &&
//            ActivityCompat.checkSelfPermission(
//                permissionContext,
//                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)

        if(requestCode == PERMISSION_REQUEST_ACCESS_LOCATION)
        {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                Toast.makeText(applicationContext, "Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return (
            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        )
    }


}