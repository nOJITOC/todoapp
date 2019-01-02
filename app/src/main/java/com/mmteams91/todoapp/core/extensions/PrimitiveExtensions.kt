package com.mmteams91.todoapp.core.extensions

import java.lang.System.currentTimeMillis

fun Number?.isNotDefault() = when (this) {
    is Double -> this != .0
    is Long -> this != 0L
    is Int -> this != 0
    is Byte -> this != 0
    is Short -> this != 0
    is Float -> this != .0f
    else -> false
}

fun <T : Number?> T?.takeIfNotDefault(): T? {
    return if (this.isNotDefault()) this else null
}

fun Number?.isDefault() = !isNotDefault()

fun Number?.isPositive() = when (this) {
    is Double -> this > .0
    is Long -> this > 0L
    is Int -> this > 0
    is Byte -> this > 0
    is Short -> this > 0
    is Float -> this > .0f
    else -> false
}

fun currentTimeInSeconds() = currentTimeMillis()/1000

fun List<*>.isInside(index: Int) = index in 0..(size - 1)
fun List<*>.isLast(index: Int) = index == size - 1

val String.Companion.EMPTY: String
    get() = ""