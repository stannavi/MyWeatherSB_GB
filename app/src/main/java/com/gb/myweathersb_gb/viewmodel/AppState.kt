package com.gb.myweathersb_gb.viewmodel

import com.gb.myweathersb_gb.domain.Weather

sealed class AppState {
    data class Success(val weatherData: Weather) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

