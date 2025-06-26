package com.gps_usage.showCoordinates.RoutesScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text
import androidx.hilt.navigation.compose.hiltViewModel

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