package com.gb.myweathersb_gb.viewmodel.citieslist

import com.gb.myweathersb_gb.domain.Weather

sealed class CityListFragmentAppState {
    data class SuccessSingle(val weatherData: Weather) : CityListFragmentAppState()
    data class SuccessMulti(val weatherList: List<Weather>) : CityListFragmentAppState()
    data class Error(val error: Throwable) : CityListFragmentAppState()
    object Loading : CityListFragmentAppState()
}

