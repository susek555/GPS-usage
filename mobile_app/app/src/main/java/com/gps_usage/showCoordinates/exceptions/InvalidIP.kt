package com.gps_usage.showCoordinates.exceptions

class InvalidIP (
    message: String = "Format of inserted IP is invalid...",
    cause: Throwable? = null
) : Exception(message, cause)