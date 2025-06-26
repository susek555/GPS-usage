package com.gps_usage.showCoordinates.routesScreen

import androidx.lifecycle.ViewModel
import com.gps_usage.showCoordinates.data.RoutesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel @Inject constructor()  : ViewModel(), KoinComponent {
    private val routesDao: RoutesDao by inject()


}