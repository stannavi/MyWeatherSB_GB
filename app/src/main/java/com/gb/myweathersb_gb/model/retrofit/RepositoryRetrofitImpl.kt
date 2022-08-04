package com.gb.myweathersb_gb.model.retrofit

import com.gb.myweathersb_gb.BuildConfig
import com.gb.myweathersb_gb.domain.City
import com.gb.myweathersb_gb.model.CommonWeatherCallback
import com.gb.myweathersb_gb.model.RepositoryWeatherByCity
import com.gb.myweathersb_gb.model.dto.WeatherDTO
import com.gb.myweathersb_gb.utils.bindDTOWithCity
import com.google.gson.GsonBuilder
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RepositoryRetrofitImpl : RepositoryWeatherByCity {
    override fun getWeather(city: City, callback: CommonWeatherCallback) {
        val retrofitImpl = Retrofit.Builder()
        retrofitImpl.baseUrl("https://api.weather.yandex.ru")
        retrofitImpl.addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        val api = retrofitImpl.build().create(WeatherAPI::class.java)
        //api.getWeather(BuildConfig.WEATHER_API_KEY, lat, lon).execute() // синхронный запрос
        api.getWeather(BuildConfig.WEATHER_API_KEY, city.lat, city.lon)
            .enqueue(object : Callback<WeatherDTO> {
                override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                    //response.raw().request // тут есть информация - кто же нас вызвал
                    if (response.isSuccessful && response.body() != null) {
                        callback.onResponse(bindDTOWithCity(response.body()!!, city))
                    } else {
                        // TODO HW callback.on??? 403 404
                        callback.onFailure(IOException("403 404"))
                    }
                }

                override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                    callback.onFailure(t as IOException) //костыль
                }
            })
    }
}