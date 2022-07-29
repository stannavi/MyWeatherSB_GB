package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.domain.getDefaultCity
import com.gb.myweathersb_gb.domain.getRussianCities
import com.gb.myweathersb_gb.domain.getWorldCities
import com.gb.myweathersb_gb.model.dto.FactDTO
import com.gb.myweathersb_gb.model.dto.WeatherDTO

class RepositoryDetailsLocalImpl: RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double, callback: MyLargeSuperCallback) {
        val list = getWorldCities().toMutableList()
        list.addAll(getRussianCities())
        val response = list.filter { it.city.lat == lat && it.city.lon == lon }
        callback.onResponse(convertModelToDto(response.first()))
    }

    private fun convertDtoToModel(weatherDTO: WeatherDTO): Weather {
        val fact: FactDTO = weatherDTO.fact
        return Weather(getDefaultCity(), fact.temp, fact.feelsLike)
    }

    private fun convertModelToDto(weather: Weather): WeatherDTO {
        val fact: FactDTO = FactDTO(weather.feelsLike, weather.temperature)
        return WeatherDTO(fact)
    }
}