package com.mmteams91.todoapp.common.presentation.view

import com.mmteams91.todoapp.common.extensions.safeSubscribe
import com.mmteams91.todoapp.common.presentation.IDisposableBinder
import com.mmteams91.todoapp.common.presentation.viewmodel.BaseViewModel
import com.mmteams91.todoapp.common.presentation.viewmodel.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

interface IView<Vm : BaseViewModel> {
    var viewModel: Vm
    val disposableBinder: IDisposableBinder


    fun viewOnStart() {
        subscribeToEvents()
        viewModel.onStart()
    }

    fun subscribeToEvents() {
        viewModel.eventsFlow()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .safeSubscribe({ subscribeToEvents() }) {
                    Timber.d("Event: $it")
                    obtainEvent(it)
                }.also { bind(it) }
    }

    fun obtainEvent(event: Event)

    fun bind(disposable: Disposable) = disposableBinder.bindToLifecycle(disposable)

}