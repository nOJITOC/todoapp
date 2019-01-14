package com.mmteams91.todoapp.common.domain.usecases.preferences

import android.content.SharedPreferences
import com.mmteams91.todoapp.common.extensions.getPrimitive
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class TrackFromPrefsUseCase @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val backgroundScheduler: Scheduler = Schedulers.newThread()
) {

    fun <T : Any> run(requestValue: Request<T>): Flowable<T> {
        return execute(requestValue)
                .subscribeOn(backgroundScheduler)
    }

    private fun <T : Any> execute(requestValue: Request<T>): Flowable<T> {
        val key = requestValue.key
        val defaultValue = requestValue.defaultValue
        lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
        return Flowable.unsafeCreate<T> { subscriber ->
            listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, changedKey ->
                if (key == changedKey) {
                    sharedPreferences.getPrimitive(key, defaultValue).let { subscriber.onNext(it) }
                }
            }
            sharedPreferences.getPrimitive(key, defaultValue).let { subscriber.onNext(it) }
            sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        }.doFinally { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
                .doOnError(Timber::e)
                .distinctUntilChanged()
    }


    fun <T : Any> run(key: String, defaultValue: T): Flowable<T> = run(Request(key, defaultValue)).cast(defaultValue.javaClass)

    inner class Request<T>(internal val key: String, internal val defaultValue: T)
}
