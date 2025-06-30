package com.gps_usage.showCoordinates.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(
    tableName = "routes"
)
data class Route(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val name: String,
    val numberOfPoints: Long,
    val time: Long
)
