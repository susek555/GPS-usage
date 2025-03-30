package com.gps_usage.showCoordinates.di

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.gps_usage.showCoordinates.LocationRepository
import com.gps_usage.showCoordinates.ShowCoordinatesViewModel
import com.gps_usage.showCoordinates.data.PointsDao
import com.gps_usage.showCoordinates.data.RoutesDao
import com.gps_usage.showCoordinates.data.RoutesDatabase
import org.koin.core.module.dsl.*
import org.koin.dsl.module

val locationModule = module {
    single {
        Room.databaseBuilder(get(), RoutesDatabase::class.java, "routes_database")
            .build()
    }
    single { get<RoutesDatabase>().pointsDao() }
    single { get<RoutesDatabase>().routesDao() }
    single { LocationRepository() }
}