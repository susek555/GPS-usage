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
    val numberOfPoints: Long, //TODO change type here to Int
    val time: Long //TODO change type here to LocalDate
)
