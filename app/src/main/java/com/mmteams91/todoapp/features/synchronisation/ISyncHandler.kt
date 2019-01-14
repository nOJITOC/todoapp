package com.mmteams91.todoapp.features.synchronisation

import com.mmteams91.todoapp.common.entities.ICommand
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

interface ISyncHandler<T : ICommand> {

    val scheduler: Scheduler
    var disposables: CompositeDisposable

    fun <P : ISyncProvider<T>> obtain(syncProvider: P) {
        syncProvider
                .syncFlow()
                .subscribeOn(scheduler)
                .subscribe({ handle(it) }, {
                    Timber.e(it)
                    obtain(syncProvider)
                })
                .also { disposables.add(it) }
    }

    fun handle(commands: List<T>)

    fun stop() {
        disposables.dispose()
        disposables = CompositeDisposable()
    }
}