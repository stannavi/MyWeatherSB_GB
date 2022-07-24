package com.gb.myweathersb_gb.model.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FactDTO(
    val condition: String,
    val daytime: String,
    @SerializedName("feels_like")
    val feelsLike: Int,
    val humidity: Int,
    val icon: String,
    @SerializedName("obs_time")
    val obsTime: Int,
    val polar: Boolean,
    @SerializedName("pressure_mm")
    val pressureMm: Int,
    @SerializedName("pressure_pa")
    val pressurePa: Int,
    val season: String,
    val temp: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
): Parcelable