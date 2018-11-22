package com.example.aleck.brolly.presenter.dependencies

import com.example.aleck.brolly.MainActivity
import com.example.aleck.brolly.application.AppComponent
import dagger.Component

/**
 * This @component class provides the blueprint for dagger generated code
 * pertaining to getting and displaying the repo lists data
 */
@WeatherScope
@Component(dependencies = [AppComponent::class], modules = [WeatherModule::class])
interface RepoListComponent{
    fun inject (mainActivity: MainActivity)
}
