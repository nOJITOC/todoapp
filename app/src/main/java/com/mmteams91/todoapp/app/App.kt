package com.fourxxi.therussianrules.app

import android.app.Application
import com.mmteams91.todoapp.app.AppComponent
import com.vk.sdk.VKSdk
import io.realm.Realm
import timber.log.Timber


class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        prepareTimber()
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build()
        VKSdk.initialize(this)
        prepareRealm()
    }

    private fun prepareRealm() {
        Realm.init(this)
    }

    private fun prepareTimber() {
        Timber.plant(Timber.DebugTree())
    }

}