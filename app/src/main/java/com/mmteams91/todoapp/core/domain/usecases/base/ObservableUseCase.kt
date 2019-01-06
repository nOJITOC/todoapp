package com.mmteams91.todoapp.core.domain.usecases.base

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCase<Q, P> protected constructor(private val backgroundScheduler: Scheduler = Schedulers.newThread()) {

    fun run(requestValue: Q): Observable<P> {
        return execute(requestValue)
                .subscribeOn(backgroundScheduler)
    }

    protected abstract fun execute(requestValue: Q): Observable<P>

}