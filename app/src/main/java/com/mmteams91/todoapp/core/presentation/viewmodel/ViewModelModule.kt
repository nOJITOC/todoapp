package com.mmteams91.todoapp.core.presentation.viewmodel

import android.arch.lifecycle.ViewModel
import com.mmteams91.todoapp.features.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun quizViewModel(viewModel: AuthViewModel): ViewModel

}