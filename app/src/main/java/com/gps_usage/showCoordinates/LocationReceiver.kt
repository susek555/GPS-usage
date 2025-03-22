package com.gps_usage.showCoordinates

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class LocationReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "LOCATION_UPDATED") {
            val latitude = intent.getStringExtra("latitude")
            val longitude = intent.getStringExtra("longitude")
            println("New location received: $latitude, $longitude")
//            Toast.makeText(context, "Location: $latitude, $longitude", Toast.LENGTH_SHORT).show()
        }
    }
}