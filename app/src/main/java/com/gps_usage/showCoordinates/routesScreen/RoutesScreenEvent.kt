package com.gps_usage.showCoordinates.routesScreen

sealed interface RoutesScreenEvent {
    data object ShowEditRouteDialog: RoutesScreenEvent
    data object HideEditRouteDialog: RoutesScreenEvent
    data object DeleteRoute: RoutesScreenEvent
    data class EditRoute(val name: String): RoutesScreenEvent
}