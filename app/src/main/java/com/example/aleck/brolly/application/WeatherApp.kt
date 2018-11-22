package com.example.aleck.brolly.application

import android.app.Application

class WeatherApp : Application() {
    lateinit var  appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}