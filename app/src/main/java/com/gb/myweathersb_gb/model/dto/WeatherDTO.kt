package com.gb.myweathersb_gb.model.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherDTO(
    val fact: FactDTO,
    val forecast: ForecastDTO,
    val info: InfoDTO,
    val now: Int,
    @SerializedName("now_dt")
    val nowDt: String
): Parcelable