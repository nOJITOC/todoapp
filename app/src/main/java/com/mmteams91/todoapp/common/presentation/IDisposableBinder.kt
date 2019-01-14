package com.mmteams91.todoapp.common.presentation

import android.arch.lifecycle.Lifecycle
import io.reactivex.disposables.Disposable

interface IDisposableBinder {

    fun bindToLifecycle(disposable: Disposable)

    fun bindToLifecycleUntil(event: Lifecycle.Event = Lifecycle.Event.ON_DESTROY, disposable: Disposable)

}