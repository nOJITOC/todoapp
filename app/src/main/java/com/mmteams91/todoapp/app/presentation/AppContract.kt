package com.mmteams91.todoapp.app.presentation

import com.mmteams91.todoapp.common.presentation.view.IView

internal interface IAppView:IView<AppViewModel>{

    fun hideProgress()

    fun showProgress()

}