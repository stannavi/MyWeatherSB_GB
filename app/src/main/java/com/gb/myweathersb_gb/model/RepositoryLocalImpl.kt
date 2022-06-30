package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.Weather

class RepositoryLocalImpl: Repository {
    override fun getListWeather(): List<Weather> {
        return listOf(Weather())
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}