package com.gps_usage.showCoordinates

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class ShowCoordinatesComponent(context: Context) {
    var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var latitude: Float = 0f
    var longitude: Float = 0f
    var permissionContext: Context = context

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
                permissionContext,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(
                permissionContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
//            &&
//            ActivityCompat.checkSelfPermission(
//                permissionContext,
//                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    private fun isLocationEnabled(): Boolean {
        //TODO
        return false
    }
}