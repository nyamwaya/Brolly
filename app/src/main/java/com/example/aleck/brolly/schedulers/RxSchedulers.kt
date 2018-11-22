package com.example.aleck.brolly.schedulers

import io.reactivex.Scheduler

interface RxSchedulers {
    fun io(): Scheduler
    fun compute(): Scheduler
    fun uiThread(): Scheduler
    fun internet(): Scheduler
    fun runOnBackground(): Scheduler
}