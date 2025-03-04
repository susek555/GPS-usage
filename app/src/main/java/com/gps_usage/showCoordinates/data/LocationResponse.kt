package com.gps_usage.showCoordinates.data

import com.gps_usage.showCoordinates.isLocationDataSuccessful

data class LocationResponse (
    var coordinates: Coordinates,
    var response: isLocationDataSuccessful
)