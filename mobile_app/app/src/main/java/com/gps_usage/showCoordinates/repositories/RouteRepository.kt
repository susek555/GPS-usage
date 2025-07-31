package com.gps_usage.showCoordinates.repositories

import app.src.main.java.com.gps_usage.showCoordinates.api.data.RouteApi
import com.gps_usage.showCoordinates.data.Route
import com.gps_usage.showCoordinates.data.apiAssociated.PostRouteDTO
import com.gps_usage.showCoordinates.exceptions.IncorrectIP
import com.gps_usage.showCoordinates.exceptions.RouteUploadFailed
import io.ktor.util.network.UnresolvedAddressException
import org.koin.android.logger.AndroidLogger
import org.koin.core.logger.Level

typealias RouteID = Long

class RouteRepository(private val api: RouteApi) {

    // returns new route ID
    suspend fun postRoute(route: Route) : RouteID {
        val routeToPost = convertRouteToPost(route)
        try {
            val postedRoute = api.postRoute(routeToPost)
            return postedRoute.id
        } catch (e: UnresolvedAddressException) {
            throw IncorrectIP()
        } catch (e: Exception) {
            throw RouteUploadFailed("Route header upload failed with name: ${route.name}", e)
        }
    }

    private fun convertRouteToPost(route: Route) : PostRouteDTO {
        return PostRouteDTO(route.name, route.numberOfPoints, route.time)
    }
}