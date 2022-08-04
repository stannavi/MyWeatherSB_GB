package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.BuildConfig
import com.gb.myweathersb_gb.domain.City
import com.gb.myweathersb_gb.model.dto.WeatherDTO
import com.gb.myweathersb_gb.utils.YANDEX_API_KEY
import com.gb.myweathersb_gb.utils.bindDTOWithCity
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class RepositoryOKHttpImpl: RepositoryWeatherByCity {
    override fun getWeather(city: City, callback: CommonWeatherCallback) {

        val client = OkHttpClient()
        val builder = Request.Builder()
        builder.addHeader(YANDEX_API_KEY, BuildConfig.WEATHER_API_KEY)
        builder.url("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
        val request: Request = builder.build()
        val call: Call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }

            override fun onResponse(call: Call, response: Response) {
                //if (response.isSuccessful) { }
                if (response.code in 200..299 && response.body != null) {
                    response.body?.let {
                        val responseString = it.string()
                        val weatherDTO = Gson().fromJson(responseString, WeatherDTO::class.java)
                        callback.onResponse(bindDTOWithCity(weatherDTO, city))
                    }
                } else {
                    // TODO HW callback.on??? 403 404
                    callback.onFailure(IOException("403 404"))
                }
            }
        })
    }
}



