package com.gb.myweathersb_gb.model.dto


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDTO(
    val fact: FactDTO
): Parcelable