package com.gps_usage.showCoordinates.dialogFactory

import com.gps_usage.showCoordinates.data.Route

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
            RouteDialogConfigState.DeleteRoute -> RouteDialogConfig(
                mainText = "Are you sure you want to delete this route?",
                onConfirm = onConfirm,
                onDismiss = onDismiss
            )
            RouteDialogConfigState.None -> null
        }
    }
}