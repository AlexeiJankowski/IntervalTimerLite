package com.hfad.intervaltimerlite

import android.app.Application
import com.hfad.intervaltimerlite.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TimerApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}