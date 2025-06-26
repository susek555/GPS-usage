package com.gps_usage.showCoordinates.routesScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text

@Composable
fun RoutesScreen(
    displayMainScreen: () -> Unit,
    viewModel: RoutesViewModel
){
    Box(){
        Text("No recorded routes found")
    }
    Button(onClick = displayMainScreen) {
        Text("BACK")
    }
}