package com.gps_usage.showCoordinates

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
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ShowCoordinates(getLocationSystem: () -> LocationResponse) {
    private val getLocation = getLocationSystem

    @Composable
    fun RunApp() {

        var latitude by remember { mutableDoubleStateOf(0.0) }
        var longitude by remember { mutableDoubleStateOf(0.0)}
        var date by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }
        var time by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time) }

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
        }


        LaunchedEffect(Unit) {
            while (true) {
                val result: LocationResponse = getLocation()

                latitude = result.coordinates!!.latitude
                longitude = result.coordinates!!.longitude
                date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time

                delay(1000)
            }
        }
    }
}