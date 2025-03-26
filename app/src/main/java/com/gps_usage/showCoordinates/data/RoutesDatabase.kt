package com.gps_usage.showCoordinates.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Route::class, Point::class],
    version = 1
)
abstract class RoutesDatabase: RoomDatabase() {

    abstract val routesDat: RoutesDao
    abstract val pointsDao: PointsDao
}