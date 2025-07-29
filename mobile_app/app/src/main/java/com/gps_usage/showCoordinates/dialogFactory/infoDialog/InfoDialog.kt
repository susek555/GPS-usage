package com.gps_usage.showCoordinates.dialogFactory.infoDialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InfoDialog(
    config: InfoDialogConfig
){
    val progress by config.progressBarFlow
        ?.collectAsState(initial = 0) //
        ?: remember { mutableIntStateOf(0) }

    AlertDialog(
        onDismissRequest = {
            if (config.canDismiss == true) {
                (config.onDismiss ?: {}).invoke()
            }
        },
        title = {
            Text(text = config.mainText)
        },
        text = {
            Column {
                if (config.hasProgressBar == true) {
                    LinearProgressIndicator(
                        progress = { progress / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            config.onDismiss?.let {
                TextButton(
                    onClick = it
                ) {
                    Text("Dismiss")
                }
            }
        }
    )
}