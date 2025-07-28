package com.gps_usage.showCoordinates.api.data

import de.jensklingenberg.ktorfit.Ktorfit
import pw.edu.pl.pap.api.ServiceCreator

class DataServiceCreator(baseUrl: String) : ServiceCreator() {
    private val ktorfit: Ktorfit = Ktorfit
}