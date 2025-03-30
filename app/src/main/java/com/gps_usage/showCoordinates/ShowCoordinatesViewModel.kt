package com.gps_usage.showCoordinates

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gps_usage.showCoordinates.data.Point
import com.gps_usage.showCoordinates.data.PointsDao
import com.gps_usage.showCoordinates.data.Route
import com.gps_usage.showCoordinates.data.RoutesDao
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.isActive
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ShowCoordinatesViewModel(
//    private val repository: LocationRepository,
//    private val pointsDao: PointsDao,
//    private val routesDao: RoutesDao
) : ViewModel(), KoinComponent {

    private val repository: LocationRepository by inject()
    private val pointsDao: PointsDao by inject()
    private val routesDao: RoutesDao by inject()

    private val _coordinatesFlow = MutableStateFlow(Pair(0.0, 0.0))
    val coordinatesFlow: StateFlow<Pair<Double, Double>> get() = _coordinatesFlow

    private val _date = MutableStateFlow<LocalDate>(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    val date: StateFlow<LocalDate> get() = _date

    private val _time = MutableStateFlow<LocalTime>(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time)
    val time: StateFlow<LocalTime> get() = _time

    private val _isLocationServiceOn = MutableStateFlow<Boolean>(false)
    val isLocationServiceOn: StateFlow<Boolean> get() = _isLocationServiceOn

    private var currentRoute: MutableState<Route?> = mutableStateOf(null)

    private val _numberOfPointsOnRoute = MutableStateFlow<Long>(0)
    val numberOfPointsOfRoute: StateFlow<Long> get() = _numberOfPointsOnRoute


    init {
        // handle incoming location data
        viewModelScope.launch {
            repository.locationFlow.collect { (latitude, longitude) ->
                _coordinatesFlow.emit(Pair(latitude, longitude))
                _date.emit(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
                _time.emit(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time)

                // add point to database
                addPointToDatabase(longitude, latitude)
            }
        }

        // handle incoming service state change
        viewModelScope.launch{
            isLocationServiceOn.drop(1).collect { newValue ->
                if(newValue) {
                    startNewRoute()
                } else {
                    stopRoute()
                }
            }
        }
    }

    fun setIsLocationServiceOn(new: Boolean) {
        _isLocationServiceOn.value = new
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
            numberOfPoints = 0
        )
        currentRoute.value!!.id = routesDao.insertRoute(currentRoute.value!!)
        println("route id = " + currentRoute.value!!.id)
    }

    private suspend fun stopRoute(){
        //TODO remove print and implement
        println("route stopped")
        _numberOfPointsOnRoute.value = 0
        //temp
        routesDao.deleteRoute(currentRoute.value!!)
    }

    // timer
    private var startTime: Long? = null
    private val _elapsedTime = MutableStateFlow<Long>(0)
    val elapsedTime: StateFlow<Long> get() = _elapsedTime

    private var timerJob: Job? = null

    fun startTimer() {
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

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        startTime = null
        _elapsedTime.value = 0
    }
}