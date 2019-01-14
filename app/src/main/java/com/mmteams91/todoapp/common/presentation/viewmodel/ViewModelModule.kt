package com.mmteams91.todoapp.common.presentation.viewmodel

import android.arch.lifecycle.ViewModel
import com.mmteams91.todoapp.app.presentation.AppViewModel
import com.mmteams91.todoapp.features.agenda.AgendaViewModel
import com.mmteams91.todoapp.features.user.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun authViewModel(viewModel: AuthViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(AppViewModel::class)
    fun appViewModel(viewModel: AppViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(AgendaViewModel::class)
    fun projectsViewModel(viewModel: AgendaViewModel): ViewModel

}