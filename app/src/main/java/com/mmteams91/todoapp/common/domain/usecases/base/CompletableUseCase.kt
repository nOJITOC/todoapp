package com.mmteams91.todoapp.common.domain.usecases.base

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


abstract class CompletableUseCase<Q> protected constructor(private val backgroundScheduler: Scheduler = Schedulers.newThread()) : UseCase<Q, Completable> {


    override fun run(requestValue: Q): Completable {
        return Observable.just(1).flatMapCompletable { execute(requestValue) }
                .subscribeOn(backgroundScheduler)
    }

    protected abstract fun execute(requestValue: Q): Completable

}
