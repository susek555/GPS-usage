package com.gps_usage.showCoordinates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gps_usage.showCoordinates.dialogFactory.StopRouteDialog
import com.gps_usage.showCoordinates.utils.formatTime

@Composable
fun MainScreen(
    startLocationService: () -> Unit,
    stopLocationService: () -> Unit,
    displayRoutesScreen: () -> Unit,
    viewModel: MainViewModel
) {
    val isServiceRunning by viewModel.isLocationServiceOn.collectAsState()

    LaunchedEffect(isServiceRunning){
        if(isServiceRunning){
            startLocationService()
        } else {
            stopLocationService()
        }
    }

    val stopRouteDialogState by viewModel.stopRouteDialogState.collectAsState()

    val location by viewModel.coordinatesFlow.collectAsState(initial = Pair(0.0, 0.0))
    val (latitude, longitude) = location

    val duration by viewModel.elapsedTime.collectAsState()

    val numberOfPoints by viewModel.numberOfPointsOnRoute.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    ) {
        Button(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 0.dp, y = 20.dp),
            onClick = displayRoutesScreen
        ){
            Text("My Routes")
        }
        Text(
            text = "LOCATION:",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.dp, y = 60.dp)
        )
        Text(
            text = "Latitude = $latitude",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.dp, y = 90.dp)
        )
        Text(
            text = "Longitude = $longitude",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.dp, y = 120.dp)
        )

        if (isServiceRunning) {
            Text(
                text = "DURATION",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = 0.dp, y = (-20).dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Text(
                text = formatTime(duration),
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = 0.dp, y = 20.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Text(
                text = "Number of points on current route : $numberOfPoints",
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = 0.dp, y = 90.dp)
            )
            StopButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = 0.dp, y = (-100).dp),
                onClick = {
                    viewModel.onEvent(MainScreenEvent.ShowStopRouteDialog)
                }
            )
        } else {
            StartButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = 0.dp, y = (-100).dp),
                onClick = {
                    viewModel.onEvent(MainScreenEvent.StartRoute)
                }
            )
        }
    }
    if(stopRouteDialogState.isVisible){
        StopRouteDialog(stopRouteDialogState.config!!)
    }
}