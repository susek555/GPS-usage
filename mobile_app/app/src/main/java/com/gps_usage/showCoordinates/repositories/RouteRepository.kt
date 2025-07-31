package com.gps_usage.showCoordinates.repositories

import app.src.main.java.com.gps_usage.showCoordinates.api.data.RouteApi
import com.gps_usage.showCoordinates.data.Route
import com.gps_usage.showCoordinates.data.apiAssociated.PostRouteDTO
import com.gps_usage.showCoordinates.exceptions.InvalidIP
import com.gps_usage.showCoordinates.exceptions.RequestTimedOut
import com.gps_usage.showCoordinates.exceptions.RouteUploadFailed
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.util.network.UnresolvedAddressException

typealias RouteID = Long

class RouteRepository(private val api: RouteApi) {

    // returns new route ID
    suspend fun postRoute(route: Route) : RouteID {
        val routeToPost = convertRouteToPost(route)
        try {
            val postedRoute = api.postRoute(routeToPost)
            return postedRoute.id
        } catch (e: UnresolvedAddressException) {
            throw InvalidIP()
        } catch (e: HttpRequestTimeoutException) {
            throw RequestTimedOut()
        } catch (e: Exception) {
            throw RouteUploadFailed("Route header upload failed with name: ${route.name}...", e)
        }
    }

    private fun convertRouteToPost(route: Route) : PostRouteDTO {
        return PostRouteDTO(route.name, route.numberOfPoints, route.time)
    }
}