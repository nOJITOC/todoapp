package com.mmteams91.todoapp.core.extensions

import com.squareup.moshi.Moshi


inline fun <reified T> Moshi.fromJson(json: String) = adapter(T::class.java).fromJson(json)