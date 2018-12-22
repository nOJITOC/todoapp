package com.mmteams91.todoapp.core.presentation.view

import android.support.annotation.StringRes
import android.view.View
import com.mmteams91.todoapp.core.extensions.safeSubscribe
import com.mmteams91.todoapp.core.presentation.IDisposableBinder
import com.mmteams91.todoapp.core.presentation.viewmodel.Event
import com.mmteams91.todoapp.core.presentation.viewmodel.IViewModel
import io.reactivex.disposables.Disposable

interface IView<Vm : IViewModel> {
    val snackBarContainer: View?
    var viewModel: Vm
    val disposableBinder: IDisposableBinder


    fun onStart() {
        viewModel.eventsFlow().safeSubscribe{
            obtainEvent(it)
        }.also { bind(it) }
        viewModel.onStart()
    }

    fun obtainEvent(event: Event)


    fun bind(disposable: Disposable) = disposableBinder.bindToLifecycle(disposable)

    fun getString(@StringRes res: Int): String
}