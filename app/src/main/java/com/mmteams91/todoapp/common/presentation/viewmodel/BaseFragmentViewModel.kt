package com.mmteams91.todoapp.common.presentation.viewmodel

import com.mmteams91.todoapp.app.Screen
import com.mmteams91.todoapp.app.presentation.AppViewModel

abstract class BaseFragmentViewModel : BaseViewModel() {
    lateinit var appViewModel: AppViewModel

    fun navigateTo(screen: Screen) = appViewModel.navigateTo(screen)

    override fun parseNetworkError(throwable: Throwable) = appViewModel.parseNetworkError(throwable)
}