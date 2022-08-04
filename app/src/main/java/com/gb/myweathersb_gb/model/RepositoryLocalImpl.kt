package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.City
import com.gb.myweathersb_gb.domain.getRussianCities
import com.gb.myweathersb_gb.domain.getWorldCities

class RepositoryLocalImpl: RepositoryWeatherByCity {
    override fun getWeather(city: City, callback: CommonWeatherCallback) {
        val list = getWorldCities().toMutableList()
        list.addAll(getRussianCities())
        val response = list.filter { it.city.lat == city.lat && it.city.lon == city.lon }
        callback.onResponse((response.first()))
    }

}