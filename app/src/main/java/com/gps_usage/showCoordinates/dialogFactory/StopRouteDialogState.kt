package com.gps_usage.showCoordinates.dialogFactory

sealed class StopRouteDialogState {
    data object None: StopRouteDialogState()
    data object NotLongEnough: StopRouteDialogState()
    data object AskForName: StopRouteDialogState()
}