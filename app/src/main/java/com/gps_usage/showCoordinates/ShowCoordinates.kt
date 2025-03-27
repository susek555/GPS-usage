package com.gps_usage.showCoordinates

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gps_usage.Location.LocationService
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun ShowCoordinatesScreen(
    startLocationService: () -> Unit,
    stopLocationService: () -> Unit,
    viewModel: ShowCoordinatesViewModel = viewModel()
) {
    val isServiceRunning by viewModel.isLocationServiceOn.collectAsState()

    val location by viewModel.coordinatesFlow.collectAsState(initial = Pair(0.0, 0.0))
    val (latitude, longitude) = location

    val date by viewModel.date.collectAsState(initial = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    val time by viewModel.time.collectAsState(initial = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "LOCATION:",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 0.dp, y = (-100).dp)
        )
        Text(
            text = "Latitude = $latitude",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 0.dp, y = (-30).dp)
        )
        Text(
            text = "Longitude = $longitude",
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 0.dp, y = 30.dp)
        )
        Text(
            text = "Date : $date",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.dp, y = 60.dp)
        )
        Text(
            text = "Time : $time",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 0.dp, y = 100.dp)
        )

        if (isServiceRunning) {
            StopButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = 0.dp, y = (-100).dp),
                onClick = {
                    stopLocationService()
                    viewModel.setIsLocationServiceOn(false)
                }
            )
        } else {
            StartButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = 0.dp, y = (-100).dp),
                onClick = {
                    startLocationService()
                    viewModel.setIsLocationServiceOn(true)
                }
            )
        }
    }

//    LaunchedEffect(viewModel.isLocationServiceOn) {
//        viewModel.isLocationServiceOn.collect { newValue ->
//            if (newValue) {
//                viewModel.startNewRoute()
//            } else {
//                viewModel.stopRoute()
//            }
//        }
//    }
}