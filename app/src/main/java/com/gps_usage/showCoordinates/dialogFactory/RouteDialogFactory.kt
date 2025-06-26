package com.gps_usage.showCoordinates.dialogFactory

class RouteDialogFactory() {
    fun create(
        state: RouteDialogConfigState,
        onConfirm: (String?) -> Unit,
        onDismiss: () -> Unit
    ) : RouteDialogConfig? {
        return when(state) {
            RouteDialogConfigState.AskForName -> RouteDialogConfig(
                mainText = "Enter name for this route",
                hasTextField = true,
                textFieldShadowText = "Name...",
                onConfirm = onConfirm,
                onDismiss = onDismiss
            )
            RouteDialogConfigState.NotLongEnough -> RouteDialogConfig(
                mainText = "Route is not long enough",
                onConfirm = onConfirm,
                onDismiss = onDismiss
            )
            RouteDialogConfigState.EditRoute -> RouteDialogConfig(
                mainText = "Change name of this route",
                hasTextField = true,
                textFieldShadowText = "Name...",
                onConfirm = onConfirm,
                onDismiss = onDismiss
            )
            RouteDialogConfigState.None -> null
        }
    }
}