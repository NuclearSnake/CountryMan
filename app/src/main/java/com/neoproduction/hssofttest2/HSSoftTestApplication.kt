package com.neoproduction.hssofttest2

import android.app.Application
import timber.log.Timber

class HSSoftTestApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
//            Timber.plant(TimberProductionTree())
        }
    }
}