package com.gps_usage.showCoordinates.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutesDao {
    @Insert
    suspend fun insertRoute(route: Route) : Long

    @Update
    suspend fun updateRoute(route: Route)

    @Delete
    suspend fun deleteRoute(route: Route)

    @Query("SELECT * FROM routes ORDER BY id ASC")
    suspend fun getAllRoutes() : List<Route>
}