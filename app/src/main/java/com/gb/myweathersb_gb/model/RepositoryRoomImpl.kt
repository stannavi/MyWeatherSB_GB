package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.MyApp
import com.gb.myweathersb_gb.domain.City
import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.model.room.WeatherEntity

class RepositoryRoomImpl: RepositoryWeatherByCity, RepositoryWeatherSave, RepositoryWeatherAvailable {
    override fun getWeather(city: City, callback: CommonWeatherCallback) {
            callback.onResponse(MyApp.getWeatherDatabase().weatherDao().getWeatherByLocation(city.lat, city.lon).let {
                if (it.size>=0) {
                    convertHistoryEntityToWeather(it).last()
                } else {
                    Weather(city)//FIXME не всегда в room может быть погода
                }
            })
    }

    override fun addWeather(weather: Weather) {
        MyApp.getWeatherDatabase().weatherDao().insertRoom(convertWeatherToEntity(weather))
    }

    override fun getWeatherAll(callback: CommonListWeatherCallback) {
        callback.onResponse(convertHistoryEntityToWeather(MyApp.getWeatherDatabase().weatherDao().getWeatherAll()))
    }

    private fun  convertHistoryEntityToWeather(entityList: List<WeatherEntity>): List<Weather> {
        return entityList.map {
            Weather(City(it.name, it.lat, it.lon), it.temperature, it.feelsLike)
        }
    }

    private fun convertWeatherToEntity(weather: Weather): WeatherEntity {
        return WeatherEntity(0, weather.city.name + "сохр. ", weather.city.lat, weather.city.lon, weather.temperature, weather.feelsLike)
    }


}



