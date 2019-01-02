package com.mmteams91.todoapp.core.presentation.view

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.mmteams91.todoapp.app.AppActivity
import com.mmteams91.todoapp.core.extensions.obtainViewModel
import com.mmteams91.todoapp.core.presentation.IDisposableBinder
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseFragmentViewModel

abstract class BaseFragment<VM : BaseFragmentViewModel>(private val viewModelType: Class<VM>) : Fragment(), IView<VM>, LifecycleObserver {
    override lateinit var viewModel: VM
    override val disposableBinder: IDisposableBinder =DisposableBinder.on(this)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel(viewModelType)
        viewModel.appViewModel = (activity as AppActivity).viewModel
        viewModel.onCreate()
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun viewOnStart() {
        super.viewOnStart()
    }


}