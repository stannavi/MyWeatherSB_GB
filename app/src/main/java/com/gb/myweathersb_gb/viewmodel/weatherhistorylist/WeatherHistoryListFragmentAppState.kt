package com.gb.myweathersb_gb.viewmodel.weatherhistorylist

import com.gb.myweathersb_gb.domain.Weather

sealed class WeatherHistoryListFragmentAppState {
    data class SuccessMulti(val weatherList: List<Weather>) : WeatherHistoryListFragmentAppState()
    data class Error(val error: Throwable) : WeatherHistoryListFragmentAppState()
    object Loading : WeatherHistoryListFragmentAppState()
}

