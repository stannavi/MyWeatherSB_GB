package com.gb.myweathersb_gb.view.details

import com.gb.myweathersb_gb.domain.Weather

fun interface OnItemClick {
    fun onItemClick(weather: Weather)
}