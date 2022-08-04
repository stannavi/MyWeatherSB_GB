package com.gb.myweathersb_gb.utils

import com.gb.myweathersb_gb.domain.City
import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.model.dto.FactDTO
import com.gb.myweathersb_gb.model.dto.WeatherDTO
import java.io.BufferedReader
import java.util.stream.Collectors

class Utils {

}

fun getLines(reader: BufferedReader): String {
    return reader.lines().collect(Collectors.joining("\n"))
}

fun bindDTOWithCity(weatherDTO: WeatherDTO, city: City): Weather {
    val fact: FactDTO = weatherDTO.fact
    return Weather(city, fact.temp, fact.feelsLike)
}

fun convertModelToDto(weather: Weather): WeatherDTO {
    val fact: FactDTO = FactDTO(weather.feelsLike, weather.temperature)
    return WeatherDTO(fact)
}