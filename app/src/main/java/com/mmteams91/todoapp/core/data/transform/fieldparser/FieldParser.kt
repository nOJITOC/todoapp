package com.mmteams91.todoapp.core.data.transform.fieldparser

/**
 * Created by mmaruknin on 11.07.18.
 */
interface FieldParser<in From, in To> {
    fun parseFields(from: From, to: To)
}