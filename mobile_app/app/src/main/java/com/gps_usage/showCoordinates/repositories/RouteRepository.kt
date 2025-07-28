package com.gps_usage.showCoordinates.repositories

import app.src.main.java.com.gps_usage.showCoordinates.api.data.RouteApi
import com.gps_usage.showCoordinates.data.Route
import com.gps_usage.showCoordinates.data.apiAssociated.PostRouteDTO

class RouteRepository(private val api: RouteApi) {

    // returns new route ID
    suspend fun postRoute(route: Route) : Long? {
        try {
            val routeToPost = convertRouteToPost(route)
            val postedRoute = api.postRoute(routeToPost)
            return postedRoute.id
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun convertRouteToPost(route: Route) : PostRouteDTO {
        return PostRouteDTO(route.name, route.numberOfPoints, route.time)
    }
}