package com.mmteams91.todoapp.core.presentation.viewmodel

import com.mmteams91.todoapp.app.AppViewModel
import com.mmteams91.todoapp.app.Screen

abstract class BaseFragmentViewModel : BaseViewModel() {
    lateinit var appViewModel: AppViewModel

    fun navigateTo(screen: Screen) = appViewModel.navigateTo(screen)
}