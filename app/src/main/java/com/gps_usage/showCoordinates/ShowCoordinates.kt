package com.gps_usage.showCoordinates

class ShowCoordinates() {
    var activity: ShowCoordinatesActivity = ShowCoordinatesActivity()

    fun runApp() {
        while (true) {
            var result: isLocationDateSuccessful = activity.getCurrentLocation()

            if (result == isLocationDateSuccessful.NO_PERMISSIONS) {

            }
        }
    }

}