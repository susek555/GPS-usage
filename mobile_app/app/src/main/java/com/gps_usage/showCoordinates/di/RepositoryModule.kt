package com.gps_usage.showCoordinates.di

import com.gps_usage.showCoordinates.repositories.PointRepository
import com.gps_usage.showCoordinates.repositories.RouteRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { RouteRepository(get()) }
    factory { PointRepository(get()) }
}