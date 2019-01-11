package com.mmteams91.todoapp.features.user.auth

import com.mmteams91.todoapp.R
import com.mmteams91.todoapp.app.ProjectsScreen
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseFragmentViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.HttpException
import javax.inject.Inject

class AuthViewModel @Inject constructor(
        private val tryAuthUseCase: TryAuthUseCase
) : BaseFragmentViewModel() {
    fun tryAuth(email: String, password: String) {
        tryAuthUseCase.run(TryAuthUseCase.Request(email, password))
                .compose(appViewModel::wrapWithProgress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    navigateTo(ProjectsScreen())
                }, { throwable ->
                    val message = when (throwable) {
                        is TryAuthUseCase.EmptyEmailException -> R.string.enter_email
                        is TryAuthUseCase.WrongEmailException -> R.string.wrong_email
                        is TryAuthUseCase.EmptyPasswordException -> R.string.enter_password
                        is HttpException -> when {
                            throwable.code() == 401 -> R.string.wrong_credentials
                            throwable.code() == 403 -> R.string.forbidden_to_access
                            else -> R.string.default_error
                        }
                        else -> R.string.default_error
                    }
                    publishEvent(SHOW_ERROR, message)
                }).also { addDisposable(it) }
    }

    companion object {
        const val SHOW_ERROR = "Show error"
    }

    // TODO: Implement the ViewModel
}
