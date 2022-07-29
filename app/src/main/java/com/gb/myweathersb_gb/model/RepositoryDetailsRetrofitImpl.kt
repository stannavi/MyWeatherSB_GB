package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.domain.getDefaultCity

class RepositoryDetailsRetrofitImpl: RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double): Weather {
        return Weather(getDefaultCity())
    }
}