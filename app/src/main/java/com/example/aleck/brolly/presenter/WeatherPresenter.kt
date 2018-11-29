package com.example.aleck.brolly.presenter

import android.content.SharedPreferences
import com.example.aleck.brolly.MainActivity
import com.example.aleck.brolly.R
import com.example.aleck.brolly.model.WeatherResponse
import com.example.aleck.brolly.schedulers.RxSchedulers
import com.example.aleck.brolly.uitls.WeatherMathUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class WeatherPresenter(
    private val rxSchedulers: RxSchedulers,
    private val weatherDataProvider: WeatherDataProvider,
    private val weatherView: WeatherView,
    private val compositeDisposable: CompositeDisposable
) {

    interface WeatherView {
        fun onWeatherDataResponse(data: WeatherResponse)
        fun onErrorResponse(errorMsg: String)
    }

    fun requestWeather(latitude: String, longitude: String) {
        compositeDisposable.add(requestWeatherData(latitude, longitude))
    }

    private fun requestWeatherData(latitude: String, longitude: String): Disposable {
        val requestCall = weatherDataProvider.provideWeatherList(latitude, longitude)
        return requestCall.subscribeOn(rxSchedulers.io())
            .observeOn(rxSchedulers.uiThread())
            .subscribe({ weatherData ->
                weatherView.onWeatherDataResponse(weatherData)
            }, { error ->
                error.message?.let { weatherView.onErrorResponse(it) }
            })
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }

}