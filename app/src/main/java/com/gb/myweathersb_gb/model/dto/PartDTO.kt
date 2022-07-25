package com.gb.myweathersb_gb.model.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PartDTO(
    val condition: String,
    val daytime: String,
    @SerializedName("feels_like")
    val feelsLike: Int,
    val humidity: Int,
    val icon: String,
    @SerializedName("part_name")
    val partName: String,
    val polar: Boolean,
    @SerializedName("prec_mm")
    val precMm: Double,
    @SerializedName("prec_period")
    val precPeriod: Int,
    @SerializedName("prec_prob")
    val precProb: Int,
    @SerializedName("pressure_mm")
    val pressureMm: Double,
    @SerializedName("pressure_pa")
    val pressurePa: Int,
    @SerializedName("temp_avg")
    val tempAvg: Int,
    @SerializedName("temp_max")
    val tempMax: Int,
    @SerializedName("temp_min")
    val tempMin: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
): Parcelable