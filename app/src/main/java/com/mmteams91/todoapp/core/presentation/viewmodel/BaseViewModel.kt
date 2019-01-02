package com.mmteams91.todoapp.core.presentation.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.SingleQueueProcessor

abstract class BaseViewModel : ViewModel(), IViewModel {
    private val eventPublisher = SingleQueueProcessor.create<Event>()

    private val disposables = CompositeDisposable()

    override fun publishEvent(event: Event) {
        eventPublisher.onNext(event)
    }

    override fun eventsFlow(): Flowable<Event> {
        return eventPublisher
    }

    override fun onCreate() {
    }

    override fun onStart() {
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun addDisposable(disposable: Disposable) = disposables.add(disposable)

}