package com.mmteams91.todoapp.common.extensions

import java.lang.System.currentTimeMillis

fun Number?.isNotDefault() = when (this) {
    is Double -> this.isNotDefault()
    is Long -> this.isNotDefault()
    is Int -> this.isNotDefault()
    is Byte -> this.isNotDefault()
    is Short -> this.isNotDefault()
    is Float -> this.isNotDefault()
    else -> false
}

fun Double.isNotDefault() = this != .0
fun Long.isNotDefault() = this != 0L
fun Int.isNotDefault() = this != 0
fun Byte.isNotDefault() = this != 0.toByte()
fun Short.isNotDefault() = this != 0.toShort()
fun Float.isNotDefault() = this != .0f

fun Double.isDefault() = !isNotDefault()
fun Long.isDefault() = !isNotDefault()
fun Int.isDefault() = !isNotDefault()
fun Byte.isDefault() = !isNotDefault()
fun Short.isDefault() = !isNotDefault()
fun Float.isDefault() = !isNotDefault()


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

fun currentTimeInSeconds() = currentTimeMillis() / 1000

fun List<*>.isInside(index: Int) = index in 0..(size - 1)
fun List<*>.isLast(index: Int) = index == size - 1

val String.Companion.EMPTY: String
    get() = ""
