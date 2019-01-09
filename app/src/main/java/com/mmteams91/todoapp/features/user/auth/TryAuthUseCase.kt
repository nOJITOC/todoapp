package com.mmteams91.todoapp.features.user.auth

import android.util.Patterns
import com.mmteams91.todoapp.core.domain.usecases.base.CompletableUseCase
import com.mmteams91.todoapp.features.user.data.IUserRepository
import io.reactivex.Completable

class TryAuthUseCase constructor(
        private val userRepository: IUserRepository,
        private val transformer: UserFromResponseTransformer
) : CompletableUseCase<TryAuthUseCase.Request>() {
    override fun execute(requestValue: Request): Completable {
        return if (isValidEmail(requestValue.email) && isValidPassword(requestValue.password)) {
            return userRepository.auth(requestValue.email, requestValue.password)
                    .doOnSuccess { userResponse ->
                        userRepository.user = transformer.transform(userResponse)
                    }.ignoreElement()
        } else Completable.error(EmptyCredentialsException())
    }

    private fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty()
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    class EmptyCredentialsException : RuntimeException()
    class Request(internal val email: String, internal val password: String)
}
