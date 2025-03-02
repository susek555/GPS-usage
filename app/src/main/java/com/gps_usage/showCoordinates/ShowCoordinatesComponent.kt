package com.gps_usage.showCoordinates

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class ShowCoordinatesComponent(context: Context) {
    var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    var latitude: Float = 0f
    var longitude: Float = 0f
}