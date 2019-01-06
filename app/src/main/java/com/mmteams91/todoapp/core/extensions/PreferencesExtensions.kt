package com.mmteams91.todoapp.core.extensions

import android.content.SharedPreferences

fun SharedPreferences.applyBoolean(key: String, value: Boolean) = edit().putBoolean(key, value).apply()
fun SharedPreferences.applyFloat(key: String, value: Float) = edit().putFloat(key, value).apply()
fun SharedPreferences.applyLong(key: String, value: Long) = edit().putLong(key, value).apply()
fun SharedPreferences.applyInt(key: String, value: Int) = edit().putInt(key, value).apply()
fun SharedPreferences.applyString(key: String, value: String) = edit().putString(key, value).apply()

@Suppress("UNCHECKED_CAST")
fun <T : Any> SharedPreferences.getPrimitive(key: String, defaultValue: T): T {
    return when (defaultValue) {
        is Boolean -> getBoolean(key, defaultValue) as T
        is Float -> getFloat(key, defaultValue) as T
        is Long -> getLong(key, defaultValue) as T
        is Int -> getInt(key, defaultValue) as T
        is String -> getString(key, defaultValue) as T
        else -> throw IllegalArgumentException("${defaultValue::class.java.simpleName} is not primitive")
    }
}