package com.mmteams91.todoapp.common.domain.usecases.preferences

import android.content.Context
import android.content.SharedPreferences
import com.mmteams91.todoapp.BuildConfig
import com.squareup.moshi.Moshi
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(context: Context, private val moshi: Moshi) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)


    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(key: String, type: Class<T>): T? {
        return when (type) {
            Boolean::class.java -> sharedPreferences.getBoolean(key, false) as T
            Double::class.java -> sharedPreferences.getFloat(key, 0f).toDouble() as T
            Float::class.java -> sharedPreferences.getFloat(key, 0f) as T
            Long::class.java -> sharedPreferences.getLong(key, 0) as T
            Int::class.java -> sharedPreferences.getInt(key, 0) as T
            String::class.java -> sharedPreferences.getString(key, null) as? T
            else -> getTyped(key, type)
        }
    }

    inline fun <reified T> getValue(key: String) = getValue(key, T::class.java)

    fun <T> putValue(key: String, value: T) {
        when (value) {
            is Boolean -> sharedPreferences.edit().putBoolean(key, value).apply()
            is Float -> sharedPreferences.edit().putFloat(key, value).apply()
            is Long -> sharedPreferences.edit().putLong(key, value).apply()
            is Int -> sharedPreferences.edit().putInt(key, value).apply()
            else -> putTyped(key, value)
        }
    }

    fun isPrimitive(type: Class<*>): Boolean {
        return when (type) {
            Boolean::class.java, Float::class.java, Long::class.java, Int::class.java, String::class.java -> true
            else -> false
        }
    }

    fun <T> trackValue(key: String, type: Class<T>): Flowable<T> {
        lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
        return Flowable.unsafeCreate<T> { subscriber ->
            listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, changedKey ->
                if (key == changedKey) {
                   getValue(key, type)?.let { subscriber.onNext(it) }
                }
            }
            getValue(key, type)?.let { subscriber.onNext(it) }
            sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
        }.doFinally { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
                .distinctUntilChanged()
    }


    fun clear() = sharedPreferences.edit().clear().apply()


    private fun putTyped(key: String, value: Any?) {
        sharedPreferences.edit().putString(key, if (value == null) null else moshi.adapter(value.javaClass).toJson(value)).apply()
    }

    private fun <T> getTyped(key: String, type: Class<T>) = sharedPreferences.getString(key, null)?.let { moshi.adapter(type).fromJson(it) }

    private object Keys {
    }

    companion object {
        const val PREFERENCES_NAME = BuildConfig.APPLICATION_ID + ".preferences"
    }
}
