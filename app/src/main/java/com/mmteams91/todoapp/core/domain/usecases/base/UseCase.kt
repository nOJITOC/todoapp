package com.mmteams91.todoapp.core.domain.usecases.base

import com.mmteams91.todoapp.core.domain.usecases.base.UseCaseRxWrapper.toCompletable
import com.mmteams91.todoapp.core.domain.usecases.base.UseCaseRxWrapper.toSingle


interface UseCase<Q, P> {
    fun run(requestValue: Q): P
    fun runSingle(requestValue: Q) = toSingle(this).run(requestValue)
}

fun <Q> UseCase<Q, Unit>.runCompletable(requestValue: Q) = toCompletable(this).run(requestValue)

fun <R> UseCase<Unit, R>.run() = run(Unit)