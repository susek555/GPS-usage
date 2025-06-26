package com.gps_usage.showCoordinates.routesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gps_usage.showCoordinates.data.Route
import com.gps_usage.showCoordinates.data.RoutesDao
import com.gps_usage.showCoordinates.dialogFactory.RouteDialogConfig
import com.gps_usage.showCoordinates.dialogFactory.RouteDialogConfigState
import com.gps_usage.showCoordinates.dialogFactory.RouteDialogFactory
import com.gps_usage.showCoordinates.dialogFactory.RouteDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel @Inject constructor()  : ViewModel(), KoinComponent {
    private val routesDao: RoutesDao by inject()

    private val _routes = MutableStateFlow<List<Route>>(emptyList())
    val routes: StateFlow<List<Route>> = _routes.asStateFlow()

    init {
        loadRoutes()
    }

    private fun loadRoutes() {
        viewModelScope.launch {
            _routes.value = routesDao.getAllRoutes()  // Runs in background
        }
    }

    private val dialogFactory = RouteDialogFactory()
    private val _isRouteDialogOpen = MutableStateFlow(false)
    private val _routeDialogConfig = MutableStateFlow<RouteDialogConfig?>(
        dialogFactory.create(
            RouteDialogConfigState.None,
            onConfirm = {},
            onDismiss = {}
        )
    )
    val routeDialogState = combine(_isRouteDialogOpen, _routeDialogConfig) { isVisible, config ->
        RouteDialogState(isVisible, config)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RouteDialogState(false, null))

    fun onEvent(event: RoutesScreenEvent) {
        when(event) {
            is RoutesScreenEvent.DeleteRoute -> {
                viewModelScope.launch {
                    routesDao.deleteRoute(event.route)
                }
            }
            is RoutesScreenEvent.EditRoute -> {
                val updatedRoute = event.route.copy(name = event.name)
                viewModelScope.launch{
                    routesDao.updateRoute(updatedRoute)
                }
            }
            RoutesScreenEvent.HideRouteDialog -> {
                _isRouteDialogOpen.value = false
            }
            is RoutesScreenEvent.ShowEditRouteDialog -> {
                setDialogConfig(DialogConfig.EDIT, event.route)
                _isRouteDialogOpen.value = true
            }
            is RoutesScreenEvent.ShowDeleteRouteDialog -> {
                setDialogConfig(DialogConfig.DELETE, event.route)
                _isRouteDialogOpen.value = true
            }
        }
    }

    private enum class DialogConfig {
        EDIT, DELETE
    }

    private fun setDialogConfig(config: DialogConfig, route: Route) {
        if(config == DialogConfig.EDIT){
            _routeDialogConfig.value = dialogFactory.create(
                RouteDialogConfigState.EditRoute,
                onConfirm = { name -> onEvent(RoutesScreenEvent.EditRoute(route, name!!))},
                onDismiss = { onEvent(RoutesScreenEvent.HideRouteDialog)}
            )
        } else {
            _routeDialogConfig.value = dialogFactory.create(
                RouteDialogConfigState.DeleteRoute,
                onConfirm = { onEvent(RoutesScreenEvent.DeleteRoute(route))},
                onDismiss = { onEvent(RoutesScreenEvent.HideRouteDialog)}
            )
        }
    }
}