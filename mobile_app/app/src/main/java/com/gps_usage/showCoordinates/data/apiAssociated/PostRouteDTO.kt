package com.gps_usage.showCoordinates.data.apiAssociated

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class PostRouteDTO (
    val name: String,
    val numberOfPoints: Int,
    val time: LocalDate
)