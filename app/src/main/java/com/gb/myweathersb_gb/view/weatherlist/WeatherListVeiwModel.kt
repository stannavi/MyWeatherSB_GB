package com.gb.myweathersb_gb.view.weatherlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.myweathersb_gb.model.Repository
import com.gb.myweathersb_gb.model.RepositoryLocalImpl
import com.gb.myweathersb_gb.model.RepositoryRemoteImpl
import com.gb.myweathersb_gb.viewmodel.AppState
import java.lang.IllegalStateException
import java.lang.Thread.sleep


class WeatherListVeiwModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()
) : ViewModel() {

    lateinit var repository: Repository

    fun getLiveData(): MutableLiveData<AppState> {
        choiceRepository()
        return liveData
    }

    private fun choiceRepository() {
        repository = if (isConnection()) {
            RepositoryRemoteImpl()
        } else {
            RepositoryLocalImpl()
        }
    }

    fun sentRequest() {
        //choiceRepository()
        liveData.value = AppState.Loading
        if ((0..3).random() == 1) {//fixme
            liveData.postValue(AppState.Error(throw  IllegalStateException("что-то пошло не так")))
        } else {
            liveData.postValue(
                AppState.Success(
                    repository.getWeather(
                        55.755826,
                        37.617299900000035
                    )
                )
            )
        }

    }

    private fun isConnection(): Boolean {
        return false
    }
}