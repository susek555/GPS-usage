package com.gps_usage.showCoordinates.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Route::class, Point::class],
    version = 1
)
@TypeConverters(
    Converters::class
)
abstract class RoutesDatabase: RoomDatabase() {

    abstract val routesDao: RoutesDao
    abstract val pointsDao: PointsDao
}