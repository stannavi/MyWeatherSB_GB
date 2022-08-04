package com.gb.myweathersb_gb.viewmodel.details

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.myweathersb_gb.MyApp
import com.gb.myweathersb_gb.domain.City
import com.gb.myweathersb_gb.domain.Weather
import com.gb.myweathersb_gb.model.*
import com.gb.myweathersb_gb.model.retrofit.RepositoryRetrofitImpl
import java.io.IOException


class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsFragmentAppState> = MutableLiveData<DetailsFragmentAppState>()
) : ViewModel() {

    lateinit var repositoryWeatherByCity: RepositoryWeatherByCity
    lateinit var repositoryWeatherAdd: RepositoryWeatherSave

    fun getLiveData(): MutableLiveData<DetailsFragmentAppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        val sp = MyApp.getMyApp().getSharedPreferences("erhrth", Context.MODE_PRIVATE)
        if (isConnection()) {
            repositoryWeatherByCity = when (2) {
                1 -> {
                    RepositoryOKHttpImpl()
                }
                2 -> {
                    RepositoryRetrofitImpl()
                }
                3 -> {
                    RepositoryWeatherLoaderImpl()
                }
                4 -> {
                    RepositoryRoomImpl()
                }
                else -> {
                    RepositoryLocalImpl()
                }
            }

            repositoryWeatherAdd = when (0) {
                1 -> {
                    RepositoryRoomImpl()
                }
                else -> {
                    RepositoryRoomImpl()
                }
            }
        } else {
            repositoryWeatherByCity = when (1) {
                1 -> {
                    RepositoryRoomImpl()
                }
                2 -> {
                    RepositoryLocalImpl()
                }
                else -> {
                    RepositoryLocalImpl()
                }
            }
            repositoryWeatherAdd = when (0) {
                1 -> {
                    RepositoryRoomImpl()
                }
                else -> {
                    RepositoryRoomImpl()
                }
            }
        }
    }


        fun getWeather(city: City) {
            liveData.value = DetailsFragmentAppState.Loading
            repositoryWeatherByCity.getWeather(city, callback)

        }

        private val callback = object : CommonWeatherCallback {
            override fun onResponse(weather: Weather) {
                /*Handler(Looper.getMainLooper()).post {

                }*/
                if (isConnection())
                    repositoryWeatherAdd.addWeather(weather)
                liveData.postValue(DetailsFragmentAppState.Success(weather))
            }

            override fun onFailure(e: IOException) {
                liveData.postValue(DetailsFragmentAppState.Error(e))
            }
        }



        fun isConnection(): Boolean {// TODO HW реализация
            return true
        }

        override fun onCleared() {
            super.onCleared()
        }
    }