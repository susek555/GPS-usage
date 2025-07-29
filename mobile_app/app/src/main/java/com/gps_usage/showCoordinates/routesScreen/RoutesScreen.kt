package com.gps_usage.showCoordinates.routesScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gps_usage.R
import com.gps_usage.showCoordinates.data.Route
import com.gps_usage.showCoordinates.dialogFactory.confirmDialog.ConfirmDialog
import com.gps_usage.showCoordinates.dialogFactory.infoDialog.InfoDialog
import com.gps_usage.showCoordinates.utils.formatDateOnly

@Composable
fun RoutesScreen(
    displayMainScreen: () -> Unit,
    viewModel: RoutesViewModel
){
    val routes by viewModel.routes.collectAsState()
    val confirmDialogState by viewModel.confirmDialogState.collectAsState()
    val infoDialogState by viewModel.infoDialogState.collectAsState()

    Scaffold(
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(RoutesScreenEvent.ShowChangeIPDialog)
                    }
                ) {
                    Text("Change IP")
                }
                FloatingActionButton(
                    onClick = displayMainScreen
                ) {
                    Text("BACK")
                }
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(routes) { route ->
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
                        Text(
                            text = formatDateOnly(route.time),
                            fontSize = 12.sp
                        )
                    }
                    IconButton(
                        onClick = {
                            viewModel.onEvent(RoutesScreenEvent.ShowUploadRouteDialog(route))
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_file_upload_24),
                            contentDescription = "Upload route"
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
    if(confirmDialogState.isVisible) {
        ConfirmDialog(confirmDialogState.config!!)
    }
    if(infoDialogState.isVisible) {
        InfoDialog(infoDialogState.config!!)
    }
}