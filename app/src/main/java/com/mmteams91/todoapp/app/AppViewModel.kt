package com.mmteams91.todoapp.app

import com.mmteams91.todoapp.app.AppViewModel.Events.CHECK_PERMISSION
import com.mmteams91.todoapp.app.AppViewModel.Events.ERROR
import com.mmteams91.todoapp.core.data.socket.SocketMessagesProvider
import com.mmteams91.todoapp.core.domain.usecases.base.run
import com.mmteams91.todoapp.core.presentation.EVENT_PREFIX
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class AppViewModel @Inject constructor(
        private val provideSocketConnectionUseCase: ProvideSocketConnectionUseCase,
        private val socketMessagesProvider: SocketMessagesProvider
) : BaseViewModel() {

    override fun onCreate() {
        super.onCreate()
        trackSocketMessages()
        provideSocketConnection()
    }

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


    fun publishShowProgress() = publishEvent(Events.SHOW_PROGRESS)

    fun publishHideProgress() = publishEvent(Events.HIDE_PROGRESS)

    fun <T> wrapWithProgress(flowable: Flowable<T>) = flowable.doOnSubscribe {
        publishShowProgress()
    }.doFinally(::publishHideProgress)

    fun <T> wrapWithProgress(single: Single<T>) = single.doOnSubscribe {
        publishShowProgress()
    }.doFinally(::publishHideProgress)

    fun wrapWithProgress(completable: Completable) = completable.doOnSubscribe {
        publishShowProgress()
    }.doFinally(::publishHideProgress)

    object Events {
        const val ERROR = EVENT_PREFIX + "Error"
        const val SHOW_PROGRESS = EVENT_PREFIX + "Show progress"
        const val HIDE_PROGRESS = EVENT_PREFIX + "Hide progress"
        const val CHECK_PERMISSION = EVENT_PREFIX + "Check permission"
    }

}