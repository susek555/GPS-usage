package com.gps_usage.showCoordinates.di

import com.gps_usage.showCoordinates.LocationRepository
import org.koin.dsl.module

val locationModule = module {
    single { LocationRepository() }
}