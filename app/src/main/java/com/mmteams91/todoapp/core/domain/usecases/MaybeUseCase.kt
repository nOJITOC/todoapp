package com.mmteams91.todoapp.core.domain.usecases

import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


abstract class MaybeUseCase<Q, P> protected constructor(private val backgroundScheduler: Scheduler = Schedulers.newThread()) : UseCase<Q, Maybe<P>> {


    override fun run(requestValue: Q): Maybe<P> {
        return execute(requestValue)
                .subscribeOn(backgroundScheduler)
    }

    protected abstract fun execute(requestValue: Q): Maybe<P>

}
