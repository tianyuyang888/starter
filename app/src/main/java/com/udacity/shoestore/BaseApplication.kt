package com.udacity.shoestore

import android.app.Application
import timber.log.Timber

class BaseApplication : Application() {


    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG)
        context = this
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        private lateinit var context: Application
        fun getApplication(): Application {
            return context
        }
    }

}