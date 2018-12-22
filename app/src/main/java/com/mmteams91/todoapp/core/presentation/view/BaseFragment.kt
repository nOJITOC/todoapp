package com.mmteams91.todoapp.core.presentation.view

import android.os.Bundle
import android.support.v4.app.Fragment
import com.mmteams91.todoapp.app.AppActivity
import com.mmteams91.todoapp.core.extensions.obtainViewModel
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseFragmentViewModel

abstract class BaseFragment<VM : BaseFragmentViewModel>(private val viewModelType: Class<VM>) : Fragment(), IView<VM> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel(viewModelType)
        viewModel.appViewModel = (activity as AppActivity).viewModel
        viewModel.onCreate()
    }


    override fun onStart() {
        super<Fragment>.onStart()
        super<IView>.onStart()
    }


}