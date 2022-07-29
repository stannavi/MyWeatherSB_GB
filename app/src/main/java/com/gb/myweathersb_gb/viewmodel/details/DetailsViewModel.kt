package com.gb.myweathersb_gb.viewmodel.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.myweathersb_gb.model.*
import com.gb.myweathersb_gb.viewmodel.citieslist.CityListFragmentAppState
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
        //liveData.postValue(DetailsFragmentAppState.Error(IllegalStateException("что-то пошло не так")))

        liveData.postValue(DetailsFragmentAppState.Success(repository.getWeather(lat, lon)))
    }

    private fun isConnection(): Boolean {
        return false
    }

    override fun onCleared() {// TODO HW ***
        super.onCleared()
    }
}