package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.domain.getRussianCities
import com.gb.myweathersb_gb.domain.getWorldCities

class RepositoryCitiesListImpl : RepositoryCitiesList {
    override fun getListCities(location: Location): List<Weather> {
        return when (location) {
            Location.Russian -> {
                getRussianCities()
            }
            Location.World -> {
                getWorldCities()
            }
        }
    }
}