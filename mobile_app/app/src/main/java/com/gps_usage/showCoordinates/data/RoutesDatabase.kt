package com.gps_usage.showCoordinates.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Route::class, Point::class],
    version = 3
)
@TypeConverters(
    Converters::class
)
abstract class RoutesDatabase: RoomDatabase() {

    abstract fun routesDao(): RoutesDao
    abstract fun pointsDao(): PointsDao
}

