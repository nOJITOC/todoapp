package com.mmteams91.todoapp.features.user.auth

import android.util.Patterns
import com.mmteams91.todoapp.core.domain.usecases.base.CompletableUseCase
import com.mmteams91.todoapp.features.user.data.IUserRepository
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TryAuthUseCase @Inject constructor(private val userRepository: IUserRepository) : CompletableUseCase<TryAuthUseCase.Request>() {
    override fun execute(requestValue: Request): Completable {
        val email = requestValue.email
        val password = requestValue.password
        return when{
            email.isBlank()->Completable.error(EmptyEmailException())
            !isValidEmail(email) ->Completable.error(WrongEmailException())
            password.isBlank()-> Completable.error(EmptyPasswordException())
            else ->userRepository.auth(requestValue.email, requestValue.password)
                    .doOnSuccess { user ->
                        userRepository.user = user
                    }.ignoreElement()
        }
        }


    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    class EmptyPasswordException : RuntimeException()
    class WrongEmailException : RuntimeException()
    class EmptyEmailException : RuntimeException()
    class Request(internal val email: String, internal val password: String)

}
