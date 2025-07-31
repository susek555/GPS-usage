package com.gps_usage.showCoordinates.routesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gps_usage.showCoordinates.data.PointsDao
import com.gps_usage.showCoordinates.data.Route
import com.gps_usage.showCoordinates.data.RoutesDao
import com.gps_usage.showCoordinates.di.changeBaseIP
import com.gps_usage.showCoordinates.dialogFactory.confirmDialog.ConfirmDialogConfig
import com.gps_usage.showCoordinates.dialogFactory.confirmDialog.ConfirmDialogConfigState
import com.gps_usage.showCoordinates.dialogFactory.confirmDialog.ConfirmDialogFactory
import com.gps_usage.showCoordinates.dialogFactory.confirmDialog.ConfirmDialogState
import com.gps_usage.showCoordinates.dialogFactory.infoDialog.InfoDialogConfig
import com.gps_usage.showCoordinates.dialogFactory.infoDialog.InfoDialogConfigState
import com.gps_usage.showCoordinates.dialogFactory.infoDialog.InfoDialogFactory
import com.gps_usage.showCoordinates.dialogFactory.infoDialog.InfoDialogState
import com.gps_usage.showCoordinates.repositories.PointRepository
import com.gps_usage.showCoordinates.repositories.RouteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.Flow
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
    private val pointsDao: PointsDao by inject()

    private val _routes = MutableStateFlow<List<Route>>(emptyList())
    val routes: StateFlow<List<Route>> = _routes.asStateFlow()

    private val _uploadProgressState = MutableStateFlow<Int>(0)
    private val uploadProgressState: Flow<Int> = _uploadProgressState.asStateFlow()

    init {
        loadRoutes()
    }

    private fun loadRoutes() {
        viewModelScope.launch {
            _routes.value = routesDao.getAllRoutes()  // Runs in background
        }
    }

    private val confirmDialogFactory = ConfirmDialogFactory()
    private val _isConfirmDialogOpen = MutableStateFlow(false)
    private val _confirmDialogConfig = MutableStateFlow<ConfirmDialogConfig?>(
        confirmDialogFactory.create(
            ConfirmDialogConfigState.None,
            onConfirm = {},
            onDismiss = {}
        )
    )
    val confirmDialogState = combine(_isConfirmDialogOpen, _confirmDialogConfig) { isVisible, config ->
        ConfirmDialogState(isVisible, config)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ConfirmDialogState(false, null))

    private val infoDialogFactory = InfoDialogFactory()
    private val _isInfoDialogOpen = MutableStateFlow(false)
    private val _infoDialogConfig = MutableStateFlow<InfoDialogConfig?>(
        infoDialogFactory.create(
            InfoDialogConfigState.None
        )
    )
    val infoDialogState = combine(_isInfoDialogOpen, _infoDialogConfig) { isVisible, config ->
        InfoDialogState(isVisible, config)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InfoDialogState(false, null))

    //TODO cache input IP
    //TODO show current ip in input field

    fun onEvent(event: RoutesScreenEvent) {
        when(event) {
            is RoutesScreenEvent.DeleteRoute -> {
                viewModelScope.launch {
                    routesDao.deleteRoute(event.route)
                    _routes.value = routesDao.getAllRoutes()
                }
                _isConfirmDialogOpen.value = false
            }
            is RoutesScreenEvent.EditRoute -> {
                val updatedRoute = event.route.copy(name = event.name)
                viewModelScope.launch{
                    routesDao.updateRoute(updatedRoute)
                    _routes.value = routesDao.getAllRoutes()
                }
                _isConfirmDialogOpen.value = false
            }
            is RoutesScreenEvent.UploadRoute -> {
                //dependencies injection
                val routeRepository: RouteRepository by inject()
                val pointRepository: PointRepository by inject()

                //show progress dialog
                _isConfirmDialogOpen.value = false
                setInfoDialogConfig(
                    InfoDialogConfigEnum.UPLOADING,
                    route = event.route
                )
                _isInfoDialogOpen.value = true

                //upload data
                viewModelScope.launch {
                    launch {
                        pointRepository.progressState.collect { progress ->
                            _uploadProgressState.value = progress
                        }
                    }
                    try {
                        val newRouteId = routeRepository.postRoute(event.route)
                        pointRepository.postPoints(
                            newRouteId,
                            pointsDao.getPointsForRoute(event.route.id).first()
                        )
                        //show success dialog
                        setInfoDialogConfig(
                            InfoDialogConfigEnum.SUCCESS,
                            route = event.route
                        )
                    } catch (e: Exception) {
                        setInfoDialogConfig(
                            InfoDialogConfigEnum.ERROR,
                            errorMessage = e.message
                        )
                    }
                }
            }
            RoutesScreenEvent.HideConfirmDialog -> {
                _isConfirmDialogOpen.value = false
            }
            is RoutesScreenEvent.ShowEditRouteDialog -> {
                setConfirmDialogConfig(ConfirmDialogConfigEnum.EDIT, event.route)
                _isConfirmDialogOpen.value = true
            }
            is RoutesScreenEvent.ShowDeleteRouteDialog -> {
                setConfirmDialogConfig(ConfirmDialogConfigEnum.DELETE, event.route)
                _isConfirmDialogOpen.value = true
            }
            is RoutesScreenEvent.ChangeIP -> {
                changeBaseIP(event.newIP)
                _isConfirmDialogOpen.value = false
            }
            RoutesScreenEvent.ShowChangeIPDialog -> {
                setConfirmDialogConfig(ConfirmDialogConfigEnum.CHANGEIP)
                _isConfirmDialogOpen.value = true
            }
            is RoutesScreenEvent.ShowUploadRouteDialog -> {
                setConfirmDialogConfig(ConfirmDialogConfigEnum.UPLOAD, event.route)
                _isConfirmDialogOpen.value = true
            }
            RoutesScreenEvent.HideInfoDialog -> {
                _isInfoDialogOpen.value = false
            }
        }
    }

    private enum class ConfirmDialogConfigEnum {
        EDIT, DELETE, CHANGEIP, UPLOAD
    }

    private enum class InfoDialogConfigEnum {
        UPLOADING, ERROR, SUCCESS
    }

    private fun setConfirmDialogConfig(config: ConfirmDialogConfigEnum, route: Route? = null) {
        when (config) {
            ConfirmDialogConfigEnum.EDIT -> {
                _confirmDialogConfig.value = confirmDialogFactory.create(
                    ConfirmDialogConfigState.EditRoute,
                    onConfirm = { name -> onEvent(RoutesScreenEvent.EditRoute(route!!, name!!))},
                    onDismiss = { onEvent(RoutesScreenEvent.HideConfirmDialog)},
                    baseTextState = route!!.name
                )
            }
            ConfirmDialogConfigEnum.DELETE -> {
                _confirmDialogConfig.value = confirmDialogFactory.create(
                    ConfirmDialogConfigState.DeleteRoute,
                    onConfirm = { onEvent(RoutesScreenEvent.DeleteRoute(route!!))},
                    onDismiss = { onEvent(RoutesScreenEvent.HideConfirmDialog)},
                    routeName = route!!.name
                )
            }
            ConfirmDialogConfigEnum.UPLOAD -> {
                _confirmDialogConfig.value = confirmDialogFactory.create(
                    ConfirmDialogConfigState.UploadRoute,
                    onConfirm = { onEvent(RoutesScreenEvent.UploadRoute(route!!))},
                    onDismiss = { onEvent(RoutesScreenEvent.HideConfirmDialog)},
                    routeName = route!!.name
                )
            }
            ConfirmDialogConfigEnum.CHANGEIP -> {
                _confirmDialogConfig.value = confirmDialogFactory.create(
                    ConfirmDialogConfigState.ChangeIP,
                    onConfirm = { newIP -> onEvent(RoutesScreenEvent.ChangeIP(newIP!!))},
                    onDismiss = { onEvent(RoutesScreenEvent.HideConfirmDialog)}
                )
            }
        }
    }

    private fun setInfoDialogConfig(config: InfoDialogConfigEnum, route: Route? = null, errorMessage: String? = null) {
        when (config) {
            InfoDialogConfigEnum.UPLOADING -> {
                _infoDialogConfig.value = infoDialogFactory.create(
                    InfoDialogConfigState.UploadingRoute,
                    progressBarFlow = uploadProgressState,
                    routeName = route!!.name
                )
            }
            InfoDialogConfigEnum.ERROR -> {
                _infoDialogConfig.value = infoDialogFactory.create(
                    InfoDialogConfigState.FailedToUploadRoute,
                    onDismiss = { onEvent(RoutesScreenEvent.HideInfoDialog)},
                    errorMessage = errorMessage!!
//                    errorMessage = errorMessage ?: "unknown error"
                )
            }
            InfoDialogConfigEnum.SUCCESS -> {
                _infoDialogConfig.value = infoDialogFactory.create(
                    InfoDialogConfigState.RouteUploadSuccess,
                    onDismiss = { onEvent(RoutesScreenEvent.HideInfoDialog)},
                    routeName = route!!.name
                )
            }
        }
    }
}