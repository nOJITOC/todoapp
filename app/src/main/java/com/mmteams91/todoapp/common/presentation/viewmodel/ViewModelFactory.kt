package com.mmteams91.todoapp.common.presentation.viewmodel

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.mmteams91.todoapp.app.App
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
        private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
        private val application: Application
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val viewModelProvider = viewModels[modelClass]
                ?: throw IllegalArgumentException("model class "
                        + modelClass
                        + " not found")
        @Suppress("UNCHECKED_CAST")
        return (viewModelProvider.get() as T).also {
            if (it is BaseViewModel) {
                (application as App).appComponent.baseViewModelComponent().inject(it)
            }
        }

    }
}
