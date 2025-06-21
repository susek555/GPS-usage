package com.gps_usage.showCoordinates.dialogFactory

sealed class StopRouteDialogConfigState {
    data object None: StopRouteDialogConfigState()
    data object NotLongEnough: StopRouteDialogConfigState()
    data object AskForName: StopRouteDialogConfigState()
}