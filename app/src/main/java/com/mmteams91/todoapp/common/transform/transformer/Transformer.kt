package com.mmteams91.todoapp.common.transform.transformer

interface Transformer<From, To> {
    fun transform(from: From): To
}