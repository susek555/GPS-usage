package com.gps_usage.showCoordinates.di

import com.gps_usage.showCoordinates.api.data.DataServiceCreator
import org.koin.core.context.GlobalContext
import org.koin.dsl.module

var apiModule = createApiModule("temp value to be replaced")

fun createApiModule(baseUrl: String) = module {
    single { DataServiceCreator(baseUrl) }
    single { get<DataServiceCreator>().createRouteApi() }
    single { get<DataServiceCreator>().createPointApi() }
}

fun changeBaseIP(newIP: String) {
    val newUrl = "http://$newIP:8080/"
    val koin = GlobalContext.get()
    koin.unloadModules(listOf(apiModule))
    apiModule = createApiModule(newUrl)
    koin.loadModules(listOf(apiModule))
}

