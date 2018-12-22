package com.mmteams91.todoapp.app

import android.support.annotation.StringRes
import com.mmteams91.todoapp.core.presentation.view.IView
import com.mmteams91.todoapp.core.presentation.viewmodel.IViewModel

interface IAppViewModel:IViewModel {

    fun publishErrorEvent(@StringRes messageRes: Int)

    fun publishErrorEvent(message: CharSequence)

    fun publishShowProgressEvent()

    fun publishHideProgressEvent()
}

internal interface IAppView:IView<AppViewModel>{

    fun hideProgress()

    fun showProgress()

}