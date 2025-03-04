package com.gps_usage.showCoordinates.data

data class LocationResponse (
    var coordinates: Coordinates? = null,
    var response: isLocationDataSuccessful
)