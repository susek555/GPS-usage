package com.gps_usage.showCoordinates.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Upsert

@Dao
interface RoutesDao {
    @Upsert
    suspend fun upsertRoute(route: Route)

    @Delete
    suspend fun deleteRoute(route: Route)
}