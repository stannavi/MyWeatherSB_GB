package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.Weather

fun interface RepositoryDetails {
    fun getWeather(lat: Double, lon: Double):Weather
}

fun interface RepositorySingle {
    fun getWeather(lat: Double, lon: Double):Weather
}

fun interface RepositoryCitiesList {
    fun getListCities(location: Location):List<Weather>
}

sealed class Location {
    object Russian: Location()
    object World: Location()
}