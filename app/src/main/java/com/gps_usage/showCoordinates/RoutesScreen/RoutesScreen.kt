package com.gps_usage.showCoordinates.RoutesScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Text

@Composable
fun RoutesScreen(
    viewModel: RoutesViewModel = viewModel()
){
    Box(){
        Text("No recorded routes found")
    }
}