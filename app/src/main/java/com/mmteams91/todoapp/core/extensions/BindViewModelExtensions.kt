package com.mmteams91.todoapp.core.extensions

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.mmteams91.todoapp.app.AppActivity
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel
import com.mmteams91.todoapp.core.presentation.viewmodel.ViewModelFactory


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
