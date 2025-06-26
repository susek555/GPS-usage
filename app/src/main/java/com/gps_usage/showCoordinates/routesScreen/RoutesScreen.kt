package com.gps_usage.showCoordinates.routesScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gps_usage.showCoordinates.utils.formatDateOnly

@Composable
fun RoutesScreen(
    displayMainScreen: () -> Unit,
    viewModel: RoutesViewModel
){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = displayMainScreen
            ) {
                Text("BACK")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.routes.value) { route ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                viewModel.onEvent(RoutesScreenEvent.ShowEditRouteDialog(route))
                            }
                    ) {
                        Text(
                            text = route.name,
                            fontSize = 20.sp
                        )
                        Text(
                            text = formatDateOnly(route.time),
                            fontSize = 12.sp
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.onEvent(RoutesScreenEvent.ShowDeleteRouteDialog(route))
                        }
                    ) {
                       Icon(
                           imageVector = Icons.Default.Delete,
                           contentDescription = "Delete route"
                       )
                    }
                }
            }
        }
    }
}