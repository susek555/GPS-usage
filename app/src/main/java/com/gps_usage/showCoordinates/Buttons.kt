package com.gps_usage.showCoordinates

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RoundButton(
    modifier: Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
    color: ButtonColors
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier,
        colors = color
    ) {
        content()
    }
}

@Composable
fun StartButton(
    modifier: Modifier,
    onClick: () -> Unit
){
    RoundButton(
        onClick = onClick,
        modifier = modifier,
        content = { Icons.Filled.LocationOn },
        color = ButtonColors( Color.Green, Color.Black, Color.Green, Color.Black)
    )
}

@Composable
fun StopButton(
    modifier: Modifier,
    onClick: () -> Unit
){
    RoundButton(
        onClick = onClick,
        modifier = modifier,
        content = { Icons.Filled.Close },
        color = ButtonColors( Color.Red, Color.Black, Color.Red, Color.Black)
    )
}