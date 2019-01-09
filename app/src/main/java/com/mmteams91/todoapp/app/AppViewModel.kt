package com.mmteams91.todoapp.app

import com.mmteams91.todoapp.app.AppViewModel.Events.CHECK_PERMISSION
import com.mmteams91.todoapp.app.AppViewModel.Events.ERROR
import com.mmteams91.todoapp.app.AppViewModel.Events.HIDE_PROGRESS
import com.mmteams91.todoapp.app.AppViewModel.Events.SHOW_PROGRESS
import com.mmteams91.todoapp.core.data.socket.SocketMessagesProvider
import com.mmteams91.todoapp.core.domain.usecases.base.run
import com.mmteams91.todoapp.core.extensions.doOnFirst
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel
import com.mmteams91.todoapp.features.user.data.IUserRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor
import javax.inject.Inject

class AppViewModel @Inject constructor(
        private val provideSocketConnectionUseCase: ProvideSocketConnectionUseCase,
        private val socketMessagesProvider: SocketMessagesProvider,
        private val userRepository: IUserRepository
) : BaseViewModel() {

    private val navigateEventPublisher = BehaviorProcessor.createDefault(prepareDefaultNavigateEvent())

    fun navigateFlow(): Flowable<Screen> = navigateEventPublisher.compose(::wrapWithProgress)

    private fun prepareDefaultNavigateEvent(): Screen {
        return AuthScreen()
    }

    override fun onCreate() {
        super.onCreate()
        trackSocketMessages()
        provideSocketConnection()
    }

    fun navigateTo(screen: Screen) = navigateEventPublisher.onNext(screen)


    private fun trackSocketMessages() {
        socketMessagesProvider.messages()
    }

    private fun provideSocketConnection() {
        addDisposable(provideSocketConnectionUseCase.run())
    }

    fun publishError(messageRes: Int) {
        publishEvent(ERROR, messageRes)
    }

    fun publishError(message: CharSequence) {
        publishEvent(ERROR, message)
    }

    fun checkPermission(permission: String, onGrant: (Boolean) -> Unit) {
        publishEvent(CHECK_PERMISSION, Pair(permission, onGrant))
    }


    fun publishShowProgress() = publishEvent(SHOW_PROGRESS)

    fun publishHideProgress() = publishEvent(HIDE_PROGRESS)

    fun <T> wrapWithProgress(flowable: Flowable<T>): Flowable<T> = flowable.doOnSubscribe {
        publishShowProgress()
    }.doOnFirst { publishHideProgress() }
            .doOnError { publishHideProgress() }
            .doOnCancel(::publishHideProgress)

    fun <T> wrapWithProgress(single: Single<T>) = single.doOnSubscribe {
        publishShowProgress()
    }.doFinally(::publishHideProgress)

    fun wrapWithProgress(completable: Completable) = completable.doOnSubscribe {
        publishShowProgress()
    }.doFinally(::publishHideProgress)

    object Events {
        const val ERROR = "Error"
        const val SHOW_PROGRESS = "Show progress"
        const val HIDE_PROGRESS = "Hide progress"
        const val CHECK_PERMISSION = "Check permission"

    }

}