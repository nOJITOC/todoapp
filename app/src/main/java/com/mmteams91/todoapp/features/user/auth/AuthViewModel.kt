package com.mmteams91.todoapp.features.user.auth

import com.mmteams91.todoapp.core.presentation.viewmodel.BaseFragmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class AuthViewModel @Inject constructor(
        private val tryAuthUseCase:TryAuthUseCase
): BaseFragmentViewModel() {
    fun tryAuth(email: String, password: String) {
        tryAuthUseCase.run(TryAuthUseCase.Request(email, password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    TODO()
                },{
                    TODO()
                })
    }

    // TODO: Implement the ViewModel
}
