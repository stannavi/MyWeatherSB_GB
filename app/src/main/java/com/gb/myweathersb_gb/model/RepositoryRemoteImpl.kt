package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.Weather

class RepositoryRemoteImpl: RepositorySingle {

    override fun getWeather(lat: Double, lon: Double): Weather {

        return Weather()
    }
}