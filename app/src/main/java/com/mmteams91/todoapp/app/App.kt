package com.mmteams91.todoapp.app

import android.app.Application
import timber.log.Timber


class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        prepareTimber()
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()
    }


    private fun prepareTimber() {
        Timber.plant(Timber.DebugTree())
    }

}