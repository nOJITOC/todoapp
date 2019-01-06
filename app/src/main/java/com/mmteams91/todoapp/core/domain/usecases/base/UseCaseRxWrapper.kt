package com.mmteams91.todoapp.core.domain.usecases.base

import io.reactivex.Completable
import io.reactivex.Single

object UseCaseRxWrapper {
    fun <Q> toCompletable(useCase: UseCase<Q, Unit>): CompletableUseCase<Q> {
        return object : CompletableUseCase<Q>() {
            override fun execute(requestValue: Q): Completable {
                return Completable.fromCallable { useCase.run(requestValue) }
            }
        }
    }

    fun <Q, P> toSingle(useCase: UseCase<Q, P>): SingleUseCase<Q, P> {
        return object : SingleUseCase<Q, P>() {
            override fun execute(requestValue: Q): Single<P> {
                return Single.fromCallable { useCase.run(requestValue) }
            }
        }
    }
}