package com.gps_usage.showCoordinates.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gps_usage.showCoordinates.MainScreen
import com.gps_usage.showCoordinates.MainViewModel
import com.gps_usage.showCoordinates.routesScreen.RoutesScreen
import com.gps_usage.showCoordinates.routesScreen.RoutesViewModel

@Composable
fun NavigationController(
    startLocationService: () -> Unit,
    stopLocationService: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "MainScreen"
    ){
        composable("MainScreen") { backStackEntry ->
            MainScreen(
                viewModel = hiltViewModel<MainViewModel>(backStackEntry),
                startLocationService = startLocationService,
                stopLocationService = stopLocationService,
                displayRoutesScreen = {
                    navController.navigate("RoutesScreen")
                }
            )
        }

        composable("RoutesScreen") { backStackEntry ->
            RoutesScreen(
                viewModel = hiltViewModel<RoutesViewModel>(backStackEntry),
                displayMainScreen = {
                    navController.navigateUp()
                }
            )
        }

    }
}
