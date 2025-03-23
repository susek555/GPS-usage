package com.gps_usage.showCoordinates.data

import androidx.room.PrimaryKey

data class Route(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val name: String,
    val points: List<Point>
)
