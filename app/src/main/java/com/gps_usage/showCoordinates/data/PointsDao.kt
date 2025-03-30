package com.gps_usage.showCoordinates.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface PointsDao {
    @Upsert
    suspend fun upsertPoint(point: Point) : Boolean

    @Query("SELECT * FROM points WHERE routeId = :selectedRoute")
    fun getPointsForRoute(selectedRoute: Long): Flow<List<Point>>
}