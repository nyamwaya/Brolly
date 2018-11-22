package com.example.aleck.brolly.presenter

import com.example.aleck.brolly.model.WeatherResponse
import com.example.aleck.brolly.networking.ServiceClientHelper
import io.reactivex.Observable

/**
 * This class provides an observable for the weather Data request
 */

class WeatherDataProvider(private  val weatherService: ServiceClientHelper.RemoteWeatherService) {
    fun provideWeatherList(latitude: String, longitude: String) : Observable<WeatherResponse>{
        return weatherService.requestWeather(latitude, longitude)
    }
}