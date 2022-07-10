package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.domain.getRussianCities
import com.gb.myweathersb_gb.domain.getWorldCities
import com.gb.myweathersb_gb.viewmodel.AppState

class RepositoryRemoteImpl: RepositorySingle {

    override fun getWeather(lat: Double, lon: Double): Weather {
        Thread {
            Thread.sleep(300L)
        }.start()
        return Weather()
    }
}