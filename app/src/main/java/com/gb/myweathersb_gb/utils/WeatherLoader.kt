package com.gb.myweathersb_gb.utils

import com.gb.myweathersb_gb.BuildConfig
import com.gb.myweathersb_gb.model.dto.WeatherDTO
import com.gb.myweathersb_gb.view.details.OnReponse
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object WeatherLoader { //TODO HW 5 try catch

    fun requestFirstVariant(lat: Double, lon: Double, onResponse: OnReponse) {
        val uri =
            URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")

        var myConnection: HttpURLConnection? = null

        myConnection = uri.openConnection() as HttpURLConnection
        myConnection.readTimeout = 5000
        myConnection.addRequestProperty(
            YANDEX_API_KEY,
            BuildConfig.WEATHER_API_KEY
        )

        Thread {
            val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
            val weatherDTO = Gson().fromJson(getLines(reader), WeatherDTO::class.java)
            onResponse.onResponse(weatherDTO)
        }.start()
    }

    fun requestSecondVariant(lat: Double, lon: Double, block: (weather: WeatherDTO) -> Unit) {
        val uri =
            URL("https://api.weather.yandex.ru/v2/informers?lat=${lat}&lon=${lon}")

        Thread {
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
                block(weatherDTO)

            } catch (e: Exception) {
            } finally {
                myConnection.disconnect()
            }
        }.start()
    }
}