package com.gb.myweathersb_gb.model.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InfoDTO(
    val lat: Double,
    val lon: Double,
    val url: String
): Parcelable