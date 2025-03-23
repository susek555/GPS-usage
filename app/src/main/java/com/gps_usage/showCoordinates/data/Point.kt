package com.gps_usage.showCoordinates.data

import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

data class Point (
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val latitude: Double,
    val longitude: Double,
    val time: LocalDateTime
)