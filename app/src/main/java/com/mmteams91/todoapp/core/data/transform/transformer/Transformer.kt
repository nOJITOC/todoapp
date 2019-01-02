package com.mmteams91.todoapp.core.data.transform.transformer

interface Transformer<From, To> {
    fun transform(from: From): To
}