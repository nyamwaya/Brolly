package com.example.aleck.brolly.presenter.dependencies

import com.example.aleck.brolly.MainActivity
import com.example.aleck.brolly.networking.ServiceClientHelper
import com.example.aleck.brolly.presenter.WeatherDataProvider
import com.example.aleck.brolly.presenter.WeatherPresenter
import com.example.aleck.brolly.schedulers.RxSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * This @module defines the dependencies needed for
 * getting and displaying repo list data
 */
@Module
class WeatherModule(val mainActivity: MainActivity) {

    @Provides
    fun providePresenter(
        rxSchedulers: RxSchedulers,
        weatherDataProvider: WeatherDataProvider,
        weatherView: WeatherPresenter.WeatherView
    ): WeatherPresenter = WeatherPresenter(
        rxSchedulers,
        weatherDataProvider,
        weatherView,
        CompositeDisposable()
    )

    @Provides
    fun providesRepoListView(): WeatherPresenter.WeatherView = mainActivity

    @Provides
    fun provideRepoData(service: ServiceClientHelper.RemoteWeatherService): WeatherDataProvider =
        WeatherDataProvider(service)

}