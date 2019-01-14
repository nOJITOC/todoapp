package com.mmteams91.todoapp.common.extensions

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.mmteams91.todoapp.app.presentation.AppActivity
import com.mmteams91.todoapp.common.presentation.viewmodel.BaseViewModel


fun <T : BaseViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>): T {
    return if (activity is AppActivity) {
        val baseActivity = activity as AppActivity
        ViewModelProviders.of(this, baseActivity.viewModelFactory).get(viewModelClass)

    } else ViewModelProviders.of(this).get(viewModelClass)
}

inline fun <reified T : BaseViewModel> Fragment.obtainViewModel() = obtainViewModel(T::class.java)

inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()

}
