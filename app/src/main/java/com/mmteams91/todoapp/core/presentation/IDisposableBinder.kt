package com.mmteams91.todoapp.core.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import io.reactivex.*
import io.reactivex.disposables.Disposable

interface IDisposableBinder {

    fun bindToLifecycle(disposable: Disposable)

    fun bindToLifecycleUntil(event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY, disposable: Disposable)

}