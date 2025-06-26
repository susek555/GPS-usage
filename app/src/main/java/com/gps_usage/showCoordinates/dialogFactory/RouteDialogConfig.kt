package com.gps_usage.showCoordinates.dialogFactory

data class RouteDialogConfig (
    val mainText: String,
    val hasTextField: Boolean = false,
    val textFieldShadowText: String = "",
    val baseTextState: String = "",
    val onConfirm: (String?) -> Unit,
    val onDismiss: () -> Unit
)