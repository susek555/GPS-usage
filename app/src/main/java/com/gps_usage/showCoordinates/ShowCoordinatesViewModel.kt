package com.gps_usage.showCoordinates

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShowCoordinatesViewModel(
): ViewModel(), KoinComponent {

    private val locationRepository: LocationRepository by inject()

    private val _latitude = MutableStateFlow(0.0)
    val latitude: StateFlow<Double> get() = _latitude

    private val _longitude = MutableStateFlow(0.0)
    val longitude: StateFlow<Double> get() = _longitude

    private val _date = MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date)
    val date: StateFlow<LocalDate> get() = _date

    private val _time = MutableStateFlow(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time)
    val time: StateFlow<LocalTime> get() = _time

    private val _isLocationServiceOn = MutableStateFlow(false)
    val isLocationServiceOn: StateFlow<Boolean> get() = _isLocationServiceOn

    fun setIsLocationServiceOn(new: Boolean) {
        _isLocationServiceOn.value = new
    }
}