package com.gps_usage.showCoordinates.dialogFactory.infoDialog

import kotlinx.coroutines.flow.Flow

data class InfoDialogConfig (
    val mainText: String,
    val hasProgressBar: Boolean? = false,
    val progressBarFlow: Flow<Int>? = null,
    val canDismiss: Boolean? = true,
    val onDismiss: (() -> Unit)? = null
)