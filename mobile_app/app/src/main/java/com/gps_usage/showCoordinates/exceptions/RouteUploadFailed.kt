package com.gps_usage.showCoordinates.exceptions

class RouteUploadFailed(
    message: String = "Failed to upload route",
    cause: Throwable? = null
) : Exception(message, cause)