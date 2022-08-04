package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.City
import com.gb.myweathersb_gb.domain.Weather
import java.io.IOException

fun interface RepositoryWeatherByCity {
    fun getWeather(city: City, callback: CommonOneWeatherCallback)
}

fun interface RepositoryWeatherAvailable {
    fun getWeatherAll(callback: CommonListWeatherCallback)
}

fun interface RepositoryWeatherAdd {
    fun addWeather(weather: Weather)
}

interface CommonOneWeatherCallback {
    fun onResponse(weather: Weather)
    fun onFailure(e: IOException)
}

interface CommonListWeatherCallback {
    fun onResponse(weather: List<Weather>)
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