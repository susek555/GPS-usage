package com.gps_usage.showCoordinates

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.gps_usage.Location.LocationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ShowCoordinatesViewModel(
): ViewModel() {

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

//    fun startLocationService() {
//        Intent(context, LocationService::class.java).apply {
//            action = LocationService.ACTION_START
//            startService(this)
//        }
//        _isLocationServiceOn.value = true
//    }

//    var latitude by remember { mutableDoubleStateOf(0.0) }
//    var longitude by remember { mutableDoubleStateOf(0.0) }
//    var date by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }
//    var time by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time) }
//    var isLocationServiceOn by remember { mutableStateOf(false) }
}