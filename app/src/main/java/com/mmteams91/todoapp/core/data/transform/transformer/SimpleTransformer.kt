package com.mmteams91.todoapp.core.data.transform.transformer

import com.mmteams91.todoapp.core.data.transform.fieldparser.FieldParser

open class SimpleTransformer<From, To>(
        private val creator: () -> To,
        private val fieldParser: FieldParser<From, To>
) : Transformer<From, To> {
    override fun transform(from: From): To {
        return creator.invoke().apply { fieldParser.parseFields(from, this) }
    }
}