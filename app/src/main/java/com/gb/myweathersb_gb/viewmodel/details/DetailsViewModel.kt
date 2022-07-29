package com.gb.myweathersb_gb.viewmodel.details

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.myweathersb_gb.model.*
import com.gb.myweathersb_gb.model.dto.WeatherDTO
import com.gb.myweathersb_gb.viewmodel.citieslist.CityListFragmentAppState
import java.io.IOException
import kotlin.random.Random


class DetailsViewModel(
    private val liveData: MutableLiveData<DetailsFragmentAppState> = MutableLiveData<DetailsFragmentAppState>()
) : ViewModel() {

    lateinit var repository: RepositoryDetails

    fun getLiveData(): MutableLiveData<DetailsFragmentAppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repository = when (1) {
            1 -> {
                RepositoryDetailsOKHttpImpl()
            }
            2 -> {
                RepositoryDetailsRetrofitImpl()
            }
            3 -> {
                RepositoryDetailsWeatherLoaderImpl()
            }

            else -> {
                RepositoryDetailsLocalImpl()
            }
        }
    }


    fun getWeather(lat: Double, lon: Double) {
        choiceRepository()
        liveData.value = DetailsFragmentAppState.Loading
        repository.getWeather(lat, lon, callback)

    }

    val callback = object: MyLargeSuperCallback {
        override fun onResponse(weatherDTO: WeatherDTO) {
            /*Handler(Looper.getMainLooper()).post {

            }*/
            liveData.postValue(DetailsFragmentAppState.Success(weatherDTO))
        }

        override fun onFailure(e: IOException) {
            liveData.postValue(DetailsFragmentAppState.Error(e))
        }

    }

    private fun isConnection(): Boolean {
        return false
    }

    override fun onCleared() {// TODO HW ***
        super.onCleared()
    }
}