package com.gb.myweathersb_gb.model.dto


import com.google.gson.annotations.SerializedName

data class InfoDTO(
    val lat: Double,
    val lon: Double,
    val url: String
)