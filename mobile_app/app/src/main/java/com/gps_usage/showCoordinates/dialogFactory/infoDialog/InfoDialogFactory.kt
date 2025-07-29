package com.gps_usage.showCoordinates.dialogFactory.infoDialog

import kotlinx.coroutines.flow.Flow

class InfoDialogFactory {
    fun create(
        state: InfoDialogConfigState,
        onDismiss: (() -> Unit)? = null,
        progressBarFlow: Flow<Int>? = null,
        routeName: String? = "[ unknown ]",
        errorMessage: String? = ""
    ) : InfoDialogConfig? {
        return when(state) {
            InfoDialogConfigState.FailedToUploadRoute -> InfoDialogConfig(
                mainText = errorMessage!!,
                onDismiss = onDismiss!!,
            )
            InfoDialogConfigState.UploadingRoute -> InfoDialogConfig(
                mainText = "Uploading route named $routeName ...",
                canDismiss = false,
                hasProgressBar = true,
                progressBarFlow = progressBarFlow!!
            )
            InfoDialogConfigState.RouteUploadSuccess -> InfoDialogConfig(
                mainText = "Successfully uploaded route named $routeName !",
                onDismiss = onDismiss!!
            )
            InfoDialogConfigState.None -> null
        }
    }
}