package com.gb.myweathersb_gb.viewmodel.details

import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.model.dto.WeatherDTO

sealed class DetailsFragmentAppState {
    data class Success(val weatherData: Weather) : DetailsFragmentAppState()
    data class Error(val error: Throwable) : DetailsFragmentAppState()
    object Loading : DetailsFragmentAppState()
}

