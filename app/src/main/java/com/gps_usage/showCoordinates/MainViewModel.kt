package com.gps_usage.showCoordinates

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gps_usage.showCoordinates.data.Point
import com.gps_usage.showCoordinates.data.PointsDao
import com.gps_usage.showCoordinates.data.Route
import com.gps_usage.showCoordinates.data.RoutesDao
import com.gps_usage.showCoordinates.dialogFactory.RouteDialogConfig
import com.gps_usage.showCoordinates.dialogFactory.RouteDialogFactory
import com.gps_usage.showCoordinates.dialogFactory.RouteDialogConfigState
import com.gps_usage.showCoordinates.dialogFactory.RouteDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel(), KoinComponent {

    private val repository: LocationRepository by inject()
    private val pointsDao: PointsDao by inject()
    private val routesDao: RoutesDao by inject()

    private val _coordinatesFlow = MutableStateFlow(Pair(0.0, 0.0))
    val coordinatesFlow: StateFlow<Pair<Double, Double>> get() = _coordinatesFlow

    private val _isLocationServiceOn = MutableStateFlow<Boolean>(false)
    val isLocationServiceOn: StateFlow<Boolean> get() = _isLocationServiceOn

    private var currentRoute: MutableState<Route?> = mutableStateOf(null)

    private val _numberOfPointsOnRoute = MutableStateFlow<Long>(0)
    val numberOfPointsOnRoute: StateFlow<Long> get() = _numberOfPointsOnRoute

    private val dialogFactory = RouteDialogFactory()
    private val _isStopRouteDialogOpen = MutableStateFlow(false)
    private val _stopRouteDialogConfig = MutableStateFlow<RouteDialogConfig?>(
        dialogFactory.create(
            RouteDialogConfigState.None,
            onConfirm = {},
            onDismiss = {}
        )
    )
    val stopRouteDialogState = combine(_isStopRouteDialogOpen, _stopRouteDialogConfig) {isVisible, config ->
        RouteDialogState(isVisible, config)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), RouteDialogState(false, null))

    fun onEvent(event: MainScreenEvent) {
        when(event) {
            MainScreenEvent.HideStopRouteDialog -> {
                _isStopRouteDialogOpen.value = false
            }
            MainScreenEvent.ShowStopRouteDialog -> {
                setDialogConfig()
                _isStopRouteDialogOpen.value = true
            }
            MainScreenEvent.StartRoute -> {
                _isLocationServiceOn.value = true
                startTimer()
                viewModelScope.launch { startNewRoute() }
            }
            is MainScreenEvent.SaveRoute -> {
                viewModelScope.launch { stopRoute(name = event.name) }
                _isLocationServiceOn.value = false
                stopTimer()
                _isStopRouteDialogOpen.value = false
            }
            MainScreenEvent.CancelRoute -> {
                viewModelScope.launch { cancelRoute() }
                _isLocationServiceOn.value = false
                stopTimer()
                _isStopRouteDialogOpen.value = false
            }
        }
    }

    init {
        // handle incoming location data
        viewModelScope.launch {
            repository.locationFlow.collect { (latitude, longitude) ->
                _coordinatesFlow.emit(Pair(latitude, longitude))

                // add point to database
                addPointToDatabase(longitude, latitude)
            }
        }
    }

    private fun setDialogConfig() {
        if(numberOfPointsOnRoute.value < 10){
            _stopRouteDialogConfig.value = dialogFactory.create(
                RouteDialogConfigState.NotLongEnough,
                onConfirm = { onEvent(MainScreenEvent.CancelRoute)},
                onDismiss = { onEvent(MainScreenEvent.HideStopRouteDialog)}
            )
        } else {
            _stopRouteDialogConfig.value = dialogFactory.create(
                RouteDialogConfigState.AskForName,
                onConfirm = { name -> onEvent(MainScreenEvent.SaveRoute(name!!))},
                onDismiss = { onEvent(MainScreenEvent.HideStopRouteDialog)}
            )
        }
    }

    private suspend fun addPointToDatabase(longitude: Double, latitude: Double) {
        //TODO remove print
        println("route : point added")
        val newPoint = Point(
            routeId = currentRoute.value!!.id,
            longitude = longitude,
            latitude = latitude,
            time = System.currentTimeMillis() - startTime!!
        )
        pointsDao.upsertPoint(newPoint)
        _numberOfPointsOnRoute.value += 1
    }

    private suspend fun startNewRoute(){
        //TODO remove prints
        println("new route started")
        currentRoute.value = Route(
            name = "",
            numberOfPoints = 0,
            time = System.currentTimeMillis()
        )
        currentRoute.value!!.id = routesDao.insertRoute(currentRoute.value!!)
        println("route id = " + currentRoute.value!!.id)
    }

    private suspend fun cancelRoute() {
        println("route cancelled")
        _numberOfPointsOnRoute.value = 0
        routesDao.deleteRoute(currentRoute.value!!)
    }

    private suspend fun stopRoute(name: String){
        //TODO remove print and temp
        //TODO if points >= 10 - name dialog
        //TODO if points < 10 - show dialog and delete route
        println("route stopped")
        println(name)
        _numberOfPointsOnRoute.value = 0
        //temp
//        routesDao.deleteRoute(currentRoute.value!!)
    }

    // timer
    private var startTime: Long? = null
    private val _elapsedTime = MutableStateFlow<Long>(0)
    val elapsedTime: StateFlow<Long> get() = _elapsedTime

    private var timerJob: Job? = null

    private fun startTimer() {
        startTime = System.currentTimeMillis()
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (isActive) {
                startTime?.let {
                    _elapsedTime.value = System.currentTimeMillis() - it
                }
                delay(1000)
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        startTime = null
        _elapsedTime.value = 0
    }
}