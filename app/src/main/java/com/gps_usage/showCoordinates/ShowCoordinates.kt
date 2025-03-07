package com.gps_usage.showCoordinates

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.gps_usage.showCoordinates.data.LocationResponse
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

class ShowCoordinates(
    private val locationService: LocationService
) {

    private lateinit var viewModel: ViewModel

    @Composable
    fun RunApp() {
        viewModel = viewModel<ShowCoordinatesViewModel>(
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ShowCoordinatesViewModel(
                        locationService
                    ) as T
                }
            }
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
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

            if (isLocationServiceOn) {
                StopButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(x = 0.dp, y = (-100).dp),
                    onClick = {
                        Intent(context, LocationService::class.java).apply {
                            action = LocationService.ACTION_STOP
                            startService(this)
                        }
                        isLocationServiceOn = false
                    }
                )
            } else {
                StartButton(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(x = 0.dp, y = (-100).dp),
                    onClick = {
                        viewModel.startLocationService()
                    }
                )
            }
        }


//        LaunchedEffect(Unit) {
//            while (true) {
//                val result: LocationResponse = getLocation()
//
//                latitude = result.coordinates!!.latitude
//                longitude = result.coordinates!!.longitude
//                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
//                time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
//
//                delay(1000)
//            }
//        }
    }
}