package com.gps_usage.showCoordinates.api.data

import app.src.main.java.com.gps_usage.showCoordinates.api.data.RouteApi
import de.jensklingenberg.ktorfit.Ktorfit
import pw.edu.pl.pap.api.ServiceCreator

class DataServiceCreator(baseUrl: String) : ServiceCreator() {
    private val ktorfit: Ktorfit = Ktorfit.Builder().baseUrl(baseUrl).httpClient(httpClient).build()

    fun createRouteApi() : RouteApi {
        return ktorfit.createRouteApi()
    }

    fun createPointApi() : PointApi {
        return ktorfit.createPointApi()
    }
}