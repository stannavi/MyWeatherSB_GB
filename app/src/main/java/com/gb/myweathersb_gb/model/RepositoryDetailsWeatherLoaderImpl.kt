package com.gb.myweathersb_gb.model

import com.gb.myweathersb_gb.BuildConfig
import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.domain.getDefaultCity
import com.gb.myweathersb_gb.model.dto.WeatherDTO
import com.gb.myweathersb_gb.utils.WeatherLoader
import com.gb.myweathersb_gb.utils.YANDEX_API_KEY
import com.gb.myweathersb_gb.utils.getLines
import com.google.gson.Gson
import okhttp3.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class RepositoryDetailsWeatherLoaderImpl: RepositoryDetails {
    override fun getWeather(lat: Double, lon: Double, callback: MyLargeSuperCallback) {

        Thread {
            val uri =
                URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")
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
                callback.onResponse(weatherDTO)

            } catch (e: IOException) {
                callback.onFailure(e)
            } finally {
                myConnection.disconnect()
            }
        }.start()
    }
}