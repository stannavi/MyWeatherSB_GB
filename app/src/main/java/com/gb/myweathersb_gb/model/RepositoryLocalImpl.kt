package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.domain.getRussianCities
import com.gb.myweathersb_gb.domain.getWorldCities

class RepositoryLocalImpl : RepositorySingle, RepositoryMulti {
    override fun getListWeather(location: Location): List<Weather> {
        return when (location) {
            Location.Russian -> {
                getRussianCities()
            }
            Location.World -> {
                getWorldCities()
            }
        }
    }

    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather()
    }
}