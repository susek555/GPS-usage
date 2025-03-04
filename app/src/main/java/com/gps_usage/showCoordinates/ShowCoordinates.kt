package com.gps_usage.showCoordinates

import android.content.Context

class ShowCoordinates(context: Context) {
    var component: ShowCoordinatesComponent = ShowCoordinatesComponent(context)

    fun runApp() {
        while (true) {
            var result: isLocationDateSuccessful = component.getCurrentLocation()

            if (result == isLocationDateSuccessful.NO_PERMISSIONS) {

            }
        }
    }

}