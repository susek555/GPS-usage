package com.gps_usage.showCoordinates.repositories

import com.gps_usage.showCoordinates.api.data.PointApi
import com.gps_usage.showCoordinates.data.Point
import com.gps_usage.showCoordinates.data.apiAssociated.PostPointDTO
import com.gps_usage.showCoordinates.exceptions.RouteUploadFailed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PointRepository(private val api: PointApi) {
    private val pageSize : Int = 200

    private val _progressState = MutableStateFlow(0)
    val progressState: StateFlow<Int> get() = _progressState

    suspend fun postPoints(routeId: Long, points: List<Point>) {
        val pointsToPost = convertPointsToPost(points)
        val totalPages = (pointsToPost.size + pageSize) / pageSize
        for (i in 1..totalPages) {
            try {
                val pointsPage = pointsToPost.subList(
                    fromIndex = (i - 1) * pageSize,
                    toIndex = minOf(i * pageSize, pointsToPost.size)
                )
                api.postPoints(routeId, pointsPage)
                _progressState.value = ((i + 1).toFloat() / totalPages * 100).toInt()
            } catch (e: Exception) {
                throw RouteUploadFailed("Route content upload failed on page $i / $totalPages...", e)
            }
        }
        _progressState.value = 0
    }

    private fun convertPointsToPost(points: List<Point>): List<PostPointDTO> {
        return points.map { point ->
            PostPointDTO(point.longitude, point.latitude, point.time)
        }
    }
}