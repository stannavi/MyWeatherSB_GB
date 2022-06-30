package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.Weather

interface Repository {
    fun getListWeather():List<Weather>
    fun getWeather(lat: Double, lon: Double):Weather
}