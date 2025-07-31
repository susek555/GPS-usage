package com.gps_usage.showCoordinates.data.apiAssociated

import kotlinx.serialization.Serializable

@Serializable
data class PostPointDTO(
    val longitude: Double,
    val latitude: Double,
    val time: Int
)
