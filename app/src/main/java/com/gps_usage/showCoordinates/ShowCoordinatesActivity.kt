package com.gps_usage.showCoordinates

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

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
                return isLocationDateSuccessful.LOCATION_OFF
            }
        } else {
            return isLocationDateSuccessful.NO_PERMISSIONS
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



    private fun isLocationEnabled(): Boolean {
        //TODO
        return false
    }


}