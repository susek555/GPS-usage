package com.gps_usage.showCoordinates

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class ShowCoordinates(openCoordinatesActivity: () -> Unit) {
    init {
        openCoordinatesActivity()
    }


    @Composable
    fun RunApp() {
        Text("Started app")

//        LaunchedEffect(Unit) {
//            while (true) {
//                val result: isLocationDateSuccessful = activity.getCurrentLocation()
//                delay(1000)
//            }
//        }
    }

}