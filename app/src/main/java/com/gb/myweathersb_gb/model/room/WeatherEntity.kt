package com.gb.myweathersb_gb.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import coil.drawable.CrossfadeDrawable
import com.gb.myweathersb_gb.R

@Entity(tableName = "weather_entity_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long=0,
    val name: String="",
    val lat: Double=1.0,
    val lon: Double=1.0,
    var temperature: Int=0,
    var feelsLike: Int=1,
    var icon: Int = R.drawable.ic_russia
)


