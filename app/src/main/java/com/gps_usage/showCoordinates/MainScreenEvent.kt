package com.gps_usage.showCoordinates

sealed interface MainScreenEvent {
    data object StartRoute: MainScreenEvent
    data object ShowStopRouteDialog: MainScreenEvent
    data object HideStopRouteDialog: MainScreenEvent
    data object CancelRoute: MainScreenEvent
    data class SaveRoute(val name: String): MainScreenEvent
}