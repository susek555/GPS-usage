package com.gps_usage.showCoordinates.di

import com.gps_usage.showCoordinates.api.data.DataServiceCreator
import org.koin.core.context.GlobalContext
import org.koin.core.module.Module
import org.koin.dsl.module

// Koin properties key
private const val BASE_URL_KEY = "baseUrl"

// API module
val apiModule: Module = module {

    factory {
        val baseUrl = getProperty(BASE_URL_KEY) ?: "http://default-ip:8080/"
        DataServiceCreator(baseUrl)
    }

    factory { get<DataServiceCreator>().createRouteApi() }
    factory { get<DataServiceCreator>().createPointApi() }
}

fun changeBaseIP(newIP: String) {
    val koin = GlobalContext.get()
    val newUrl = "http://$newIP:8080/"
    koin.setProperty(BASE_URL_KEY, newUrl)
    println("new url = $newUrl")
}

