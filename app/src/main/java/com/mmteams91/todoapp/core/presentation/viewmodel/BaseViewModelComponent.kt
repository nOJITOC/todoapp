package com.mmteams91.todoapp.core.presentation.viewmodel

import dagger.Subcomponent

@Subcomponent
interface BaseViewModelComponent {
    fun inject(baseViewModel: BaseViewModel)
}
