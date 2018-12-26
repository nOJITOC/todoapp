package com.mmteams91.todoapp.core.presentation.view

import android.view.View
import com.mmteams91.todoapp.core.extensions.safeSubscribe
import com.mmteams91.todoapp.core.presentation.IDisposableBinder
import com.mmteams91.todoapp.core.presentation.viewmodel.Event
import com.mmteams91.todoapp.core.presentation.viewmodel.IViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

interface IView<Vm : IViewModel> {
    val snackBarContainer: View?
    var viewModel: Vm
    val disposableBinder: IDisposableBinder


    fun onStart() {
        subscribeToEvents()
        viewModel.onStart()
    }

    fun subscribeToEvents() {
        viewModel.eventsFlow()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .safeSubscribe({ subscribeToEvents() }) {
                    obtainEvent(it)
                }.also { bind(it) }
    }

    fun obtainEvent(event: Event)

    fun bind(disposable: Disposable) = disposableBinder.bindToLifecycle(disposable)

}