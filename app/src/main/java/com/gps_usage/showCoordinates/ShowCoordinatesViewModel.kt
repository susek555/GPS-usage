package com.gps_usage.showCoordinates

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShowCoordinatesViewModel : ViewModel(), KoinComponent {

    private val repository: LocationRepository by inject()

    private val _coordinatesFlow = MutableSharedFlow<Pair<Double, Double>>(replay = 1)
    val coordinatesFlow: SharedFlow<Pair<Double, Double>> get() = _coordinatesFlow.asSharedFlow()

    private val _date = MutableSharedFlow<LocalDate>(replay = 1)
    val date: SharedFlow<LocalDate> get() = _date

    private val _time = MutableSharedFlow<LocalTime>(replay = 1)
    val time: SharedFlow<LocalTime> get() = _time

    private val _isLocationServiceOn = MutableSharedFlow<Boolean>(replay = 1)
    val isLocationServiceOn: SharedFlow<Boolean> get() = _isLocationServiceOn

    init {
        viewModelScope.launch {
            repository.locationFlow.collect { (latitude, longitude) ->
                _coordinatesFlow.emit(Pair(latitude, longitude))
                _date.emit(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
                _time.emit(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time)
            }
        }
    }

    fun setIsLocationServiceOn(new: Boolean) {
        viewModelScope.launch {
            _isLocationServiceOn.emit(new)
        }
    }
}