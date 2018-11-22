package com.example.aleck.brolly.application

import com.example.aleck.brolly.schedulers.AppRxSchedulers
import com.example.aleck.brolly.schedulers.RxSchedulers
import dagger.Module
import dagger.Provides

/**
 * This module provides the RxScheduler dependencies
 * to be used for the networking concurrency logic
 */
@Module
class RxModule {

    @Provides
    fun provideRxSchedulers(): RxSchedulers {
        return AppRxSchedulers()
    }
}