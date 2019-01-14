package com.mmteams91.todoapp.common.presentation.viewmodel

import dagger.Subcomponent

@Subcomponent
interface BaseViewModelComponent {
    fun inject(baseViewModel: BaseViewModel)
}
