package com.mmteams91.todoapp.core.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.Event.*
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DisposableBinder private constructor(lifecycleOwner: LifecycleOwner) : IDisposableBinder, LifecycleObserver {

    private val disposables = mutableMapOf<Lifecycle.Event, CompositeDisposable>()
    internal var lastEvent = Lifecycle.Event.ON_CREATE

    init {
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED))
            lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun bindToLifecycle(disposable: Disposable) {
        addDisposable(getEventForDispose(lastEvent), disposable)
    }

    override fun bindToLifecycleUntil(event: Lifecycle.Event, disposable: Disposable) {
        addDisposable(event, disposable)
    }

    private fun getEventForDispose(event: Lifecycle.Event): Lifecycle.Event {
        return when (event) {
            ON_CREATE -> ON_DESTROY
            ON_START -> ON_STOP
            ON_RESUME -> ON_PAUSE
            else -> ON_DESTROY
        }
    }

    private fun addDisposable(eventForDispose: Lifecycle.Event, disposable: Disposable) {
        if (eventForDispose <= lastEvent) disposable.dispose()
        else disposables[eventForDispose]?.add(disposable)
                ?: let { disposables[eventForDispose] = CompositeDisposable(disposable) }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onEvent(owner: LifecycleOwner, event: Lifecycle.Event) {
        lastEvent = event
        disposables[event]?.clear()
        disposables[ON_ANY]?.clear()
        if (event == Lifecycle.Event.ON_DESTROY) {
            owner.lifecycle.removeObserver(this)
        }
    }

    companion object {
        fun on(lifecycleOwner: LifecycleOwner) = DisposableBinder(lifecycleOwner)
    }
}