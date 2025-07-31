package com.gps_usage.showCoordinates.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Entity(
    tableName = "routes"
)
@Serializable
data class Route(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val name: String,
    val numberOfPoints: Int, //was Long
    val time: LocalDate //was Long
)
