package com.gps_usage.showCoordinates.exceptions

class IncorrectIP (
    message: String = "Inserted IP does not work...",
    cause: Throwable? = null
) : Exception(message, cause)