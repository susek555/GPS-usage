package com.gps_usage.showCoordinates.exceptions

class RequestTimedOut (
    message: String = "Request to inserted IP was timed out...",
    cause: Throwable? = null
) : Exception(message, cause)