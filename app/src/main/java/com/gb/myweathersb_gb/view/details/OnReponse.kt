package com.gb.myweathersb_gb.view.details

import com.gb.myweathersb_gb.model.dto.WeatherDTO

fun interface OnReponse {
    fun onResponse(weather: WeatherDTO)
}