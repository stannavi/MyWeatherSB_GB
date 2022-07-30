package com.gb.myweathersb_gb.model.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FactDTO(
    @SerializedName("feels_like")
    val feelsLike: Int,
    val temp: Int,
    val icon: String="skc_d"
): Parcelable