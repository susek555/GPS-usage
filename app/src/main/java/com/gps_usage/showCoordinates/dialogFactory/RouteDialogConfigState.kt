package com.gps_usage.showCoordinates.dialogFactory

sealed class RouteDialogConfigState {
    data object None: RouteDialogConfigState()
    data object NotLongEnough: RouteDialogConfigState()
    data object AskForName: RouteDialogConfigState()
    data object EditRoute: RouteDialogConfigState()
}