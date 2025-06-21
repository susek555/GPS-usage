package com.gps_usage.showCoordinates.dialogFactory


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*

@Composable
fun StopRouteDialog(
    config: StopRouteDialogConfig
){
    var inputText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = config.onDismiss,
        title = {
            Text(text = config.mainText)
        },
        text = {
            if (config.hasTextField) {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text(config.textFieldShadowText) }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    config.onConfirm(inputText)
                },
                enabled = (inputText.isNotEmpty() || !config.hasTextField)
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = config.onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}