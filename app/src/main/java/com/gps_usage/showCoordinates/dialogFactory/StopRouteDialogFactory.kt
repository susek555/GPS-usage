package com.gps_usage.showCoordinates.dialogFactory

class StopRouteDialogFactory() {
    fun create(
        state: StopRouteDialogConfigState,
        onConfirm: (String?) -> Unit,
        onDismiss: () -> Unit
    ) : StopRouteDialogConfig? {
        return when(state) {
            StopRouteDialogConfigState.AskForName -> StopRouteDialogConfig(
                mainText = "Enter name for this route",
                hasTextField = true,
                textFieldShadowText = "Name...",
                onConfirm = onConfirm,
                onDismiss = onDismiss
            )
            StopRouteDialogConfigState.NotLongEnough -> StopRouteDialogConfig(
                mainText = "Route is not long enough",
                onConfirm = onConfirm,
                onDismiss = onDismiss
            )
            StopRouteDialogConfigState.None -> null
        }
    }
}