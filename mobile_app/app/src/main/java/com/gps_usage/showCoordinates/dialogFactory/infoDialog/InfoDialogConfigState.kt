package com.gps_usage.showCoordinates.dialogFactory.infoDialog

sealed class InfoDialogConfigState {
    data object None: InfoDialogConfigState()
    data object UploadingRoute: InfoDialogConfigState()
    data object FailedToUploadRoute: InfoDialogConfigState()
    data object RouteUploadSuccess: InfoDialogConfigState()
}