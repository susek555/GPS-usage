package com.gps_usage.showCoordinates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LocationRepository(): KoinComponent {
//    private val receiver: LocationReceiver by inject()

    private val _locationFlow = MutableSharedFlow<Pair<Double, Double>>(replay = 1)
    val locationFlow = _locationFlow.asSharedFlow()

    fun updateLocation(latitude: Double, longitude: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            _locationFlow.emit(Pair(latitude, longitude))
        }
    }
}