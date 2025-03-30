package com.gps_usage.showCoordinates.dialogFactory

class StopRouteDialogFactory(
    private val onConfirm: () -> Unit
) {
    fun create(state: StopRouteDialogState) : StopRouteDialogConfig? {
        return when(state) {
            StopRouteDialogState.AskForName -> StopRouteDialogConfig(
                mainText = "Enter name for this route",
                hasTextField = true,
                textFieldShadowText = "Name...",
                onConfirm = {
                    onConfirm()
                }
            )
            StopRouteDialogState.NotLongEnough -> StopRouteDialogConfig(
                mainText = "Route is not long enough",
                onConfirm = {
                    onConfirm()
                }
            )
            StopRouteDialogState.None -> null
        }
    }
}