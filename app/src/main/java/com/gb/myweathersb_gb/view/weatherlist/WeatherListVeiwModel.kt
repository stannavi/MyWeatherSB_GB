package com.gb.myweathersb_gb.view.weatherlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.myweathersb_gb.viewmodel.AppState
import java.lang.Thread.sleep


class WeatherListVeiwModel(val liveData: MutableLiveData<AppState> = MutableLiveData<AppState>()) : ViewModel() {

    fun sentRequest() {
        liveData.value = AppState.Loading
        Thread {
            sleep(2000L)
            liveData.postValue(AppState.Success(Any()))
        }.start()
    }
}