package com.gps_usage.showCoordinates.routesScreen

import com.gps_usage.showCoordinates.data.Route

sealed interface RoutesScreenEvent {
    data class ShowEditRouteDialog(val route: Route): RoutesScreenEvent
    data class ShowDeleteRouteDialog(val route: Route): RoutesScreenEvent
    data object HideConfirmDialog: RoutesScreenEvent
    data class DeleteRoute(val route: Route): RoutesScreenEvent
    data class EditRoute(val route: Route, val name: String): RoutesScreenEvent
    data object ShowChangeIPDialog: RoutesScreenEvent
    data class ChangeIP(val newIP: String): RoutesScreenEvent
    data class ShowUploadRouteDialog(val route: Route): RoutesScreenEvent
    data object HideInfoDialog: RoutesScreenEvent
    data class UploadRoute(val route: Route): RoutesScreenEvent
}