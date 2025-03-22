package com.gps_usage.showCoordinates
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LocationRepository(): KoinComponent {
    private val receiver: LocationReceiver by inject()


}