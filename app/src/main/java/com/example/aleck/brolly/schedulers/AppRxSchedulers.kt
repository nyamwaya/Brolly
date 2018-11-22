package com.example.aleck.brolly.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors


/**
 * This class implements RxSchedulers and provides the
 * concrete dependencies for the different types of schedulers
 */
class AppRxSchedulers : RxSchedulers {

    val backgroundExecutor = Executors.newCachedThreadPool()
    val BACKGROUND_SCHEDULERS = Schedulers.from(backgroundExecutor)
    val internetExecutor = Executors.newCachedThreadPool()
    val INTERNET_SCHEDULERS = Schedulers.from(internetExecutor)

    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun compute(): Scheduler {
        return Schedulers.computation()
    }

    override fun uiThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun internet(): Scheduler {
        return INTERNET_SCHEDULERS
    }

    override fun runOnBackground(): Scheduler {
        return BACKGROUND_SCHEDULERS
    }

}