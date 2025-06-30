package com.gps_usage.showCoordinates.di

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gps_usage.showCoordinates.LocationRepository
import com.gps_usage.showCoordinates.data.RoutesDatabase
import org.koin.dsl.module

val locationModule = module {
    single {
        Room.databaseBuilder(get(), RoutesDatabase::class.java, "routes_database")
            .fallbackToDestructiveMigration().build()
    }
    single { get<RoutesDatabase>().pointsDao() }
    single { get<RoutesDatabase>().routesDao() }
    single { LocationRepository() }
}