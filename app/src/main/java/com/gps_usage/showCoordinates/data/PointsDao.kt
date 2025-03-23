package com.gps_usage.showCoordinates.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PointsDao {
    @Upsert
    suspend fun upsertPoint(point: Point)

    @Query("SELECT * FROM points WHERE routeId = :selectedRoute")
    fun getPointsForRoute(selectedRoute: Long): List<Point>
}