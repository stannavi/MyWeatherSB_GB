package com.gb.myweathersb_gb

import android.app.Application

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        myApp = this
    }

    companion object {
        private var myApp: MyApp? = null
        fun getMyApp() = myApp!!
    }
}