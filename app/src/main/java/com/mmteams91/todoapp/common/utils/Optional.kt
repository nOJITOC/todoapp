package com.mmteams91.todoapp.common.utils


import java.io.Serializable

class Optional<T> internal constructor(private var value: T? = null) : Serializable {


    fun isPresent() = value != null


    fun get(): T = value ?: throwExc()

    private fun throwExc(): T {
        throw NullPointerException()
    }

    fun or(defaultValue: T) = value ?: defaultValue

    fun orNull(): T? = value

    companion object {

        fun <T> empty(): Optional<T> = Optional()

        fun <T> of(reference: T): Optional<T> {
            return Optional(reference)
        }

        fun <T> fromNullable(nullableReference: T?): Optional<T> {
            return Optional(nullableReference)
        }
    }


}
