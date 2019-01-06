package com.mmteams91.todoapp.core.domain.usecases.base

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


abstract class FlowableUseCase<Q, P> protected constructor(private val backgroundScheduler: Scheduler = Schedulers.newThread()) : UseCase<Q, Flowable<P>> {


    override fun run(requestValue: Q): Flowable<P> {
        return execute(requestValue)
                .subscribeOn(backgroundScheduler)
    }

    protected abstract fun execute(requestValue: Q): Flowable<P>

}
