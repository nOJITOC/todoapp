package com.mmteams91.todoapp.core.domain.usecases.preferences

import android.content.SharedPreferences
import com.mmteams91.todoapp.core.extensions.EMPTY
import com.mmteams91.todoapp.core.utils.Optional
import com.squareup.moshi.Moshi
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class TrackObjectFromPrefs @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val moshi: Moshi,
        private val backgroundScheduler: Scheduler = Schedulers.newThread()
) {

    fun <T : Any> run(requestValue: Request<T>): Flowable<Optional<T>> {
        return execute(requestValue)
                .subscribeOn(backgroundScheduler)
    }

    private fun <T : Any> execute(requestValue: Request<T>): Flowable<Optional<T>> {
        val key = requestValue.key
        val type = requestValue.type
        lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
        return Flowable.unsafeCreate<Optional<T>> { subscriber ->
            listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
                if (key == changedKey) {
                    subscriber.onNext(getOptional(key, type))
                }
            }
            subscriber.onNext(getOptional(key, type))
            sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        }.doFinally { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
                .doOnError(Timber::e)
                .distinctUntilChanged()
    }

    private fun <T : Any> getOptional(key: String, type: Class<T>): Optional<T> {
        return sharedPreferences.getString(key, String.EMPTY)
                ?.let { moshi.adapter(type).fromJson(it) }
                .let { Optional.fromNullable(it) }
    }


    fun <T : Any> run(key: String, type: Class<T>): Flowable<Optional<T>> = run(Request(key, type))

    inner class Request<T>(internal val key: String, internal val type: Class<T>)

}