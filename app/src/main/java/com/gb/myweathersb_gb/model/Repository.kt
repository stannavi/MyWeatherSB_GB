package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.City
import com.gb.myweathersb_gb.domain.Weather
import java.io.IOException

fun interface RepositoryWeatherByCity {
    fun getWeather(city: City, callback: CommonWeatherCallback)
}

fun interface RepositoryWeatherAvailable {
    fun getWeatherAll(callback: CommonListWeatherCallback)
}

fun interface RepositoryWeatherSave {
    fun addWeather(weather: Weather)
}

interface CommonWeatherCallback {
    fun onResponse(weather: Weather)
    fun onFailure(e: IOException)
}

interface CommonListWeatherCallback {
    fun onResponse(weather: List<Weather>)
    fun onFailure(e: IOException)
}

fun interface RepositoryCitiesList {
    fun getListCities(location: Location):List<Weather>
}

sealed class Location {
    object Russian: Location()
    object World: Location()
}