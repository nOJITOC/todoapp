package com.mmteams91.todoapp.core.utils

import com.mmteams91.todoapp.BuildConfig
import timber.log.Timber

object Logger {

    fun d(message: String, vararg args: Any) {
        if (BuildConfig.DEBUG) Timber.d(message, args)
    }
}