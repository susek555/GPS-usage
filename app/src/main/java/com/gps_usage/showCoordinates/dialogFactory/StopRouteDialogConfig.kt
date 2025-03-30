package com.gps_usage.showCoordinates.dialogFactory

data class StopRouteDialogConfig (
    val mainText: String,
    val hasTextField: Boolean = false,
    val textFieldShadowText: String = "",
    val onConfirm: () -> Unit
)