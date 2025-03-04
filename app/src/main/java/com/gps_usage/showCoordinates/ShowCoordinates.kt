package com.gps_usage.showCoordinates

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import com.gps_usage.showCoordinates.data.LocationResponse
import kotlinx.coroutines.delay
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class ShowCoordinates(getLocationSystem: () -> LocationResponse) {
    private val getLocation = getLocationSystem

    @Composable
    fun RunApp() {
        Text("Started app")

        var latitude by remember { mutableDoubleStateOf(0.0) }
        var longitude by remember { mutableDoubleStateOf(0.0)}

        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
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
        }


        LaunchedEffect(Unit) {
            while (true) {
                val result: LocationResponse = getLocation()

                latitude = result.coordinates!!.latitude
                longitude = result.coordinates!!.longitude

                delay(1000)
            }
        }
    }
}