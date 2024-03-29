package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.BuildConfig
import com.gb.myweathersb_gb.domain.City
import com.gb.myweathersb_gb.model.dto.WeatherDTO
import com.gb.myweathersb_gb.utils.YANDEX_API_KEY
import com.gb.myweathersb_gb.utils.bindDTOWithCity
import com.gb.myweathersb_gb.utils.getLines
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RepositoryWeatherLoaderImpl: RepositoryWeatherByCity {
    override fun getWeather(city: City, callback: CommonWeatherCallback) {

        Thread {
            val uri =
                URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")
            var myConnection: HttpsURLConnection? = null
            myConnection = uri.openConnection() as HttpsURLConnection
            try {
                myConnection.readTimeout = 5000
                myConnection.addRequestProperty(
                    YANDEX_API_KEY,
                    BuildConfig.WEATHER_API_KEY
                )

                val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
                callback.onResponse(bindDTOWithCity(weatherDTO, city))

            } catch (e: IOException) {
                callback.onFailure(e)
            } finally {
                myConnection.disconnect()
            }
        }.start()
    }
}