package com.gps_usage.showCoordinates.data.apiAssociated

import kotlinx.datetime.LocalTime

data class PostRouteDTO (
    val name: String,
    val numberOfPoints: Int,
    val time: LocalTime
)