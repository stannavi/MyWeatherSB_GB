package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.model.dto.WeatherDTO
import java.io.IOException

fun interface RepositoryDetails {
    fun getWeather(lat: Double, lon: Double, callback: MyLargeSuperCallback)
}

interface MyLargeSuperCallback {
    fun onResponse(weatherDTO: WeatherDTO)
    fun onFailure(e: IOException)
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