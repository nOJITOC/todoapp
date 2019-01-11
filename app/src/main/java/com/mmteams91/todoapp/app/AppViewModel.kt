package com.mmteams91.todoapp.app

import com.mmteams91.todoapp.R
import com.mmteams91.todoapp.app.AppViewModel.Events.CHECK_PERMISSION
import com.mmteams91.todoapp.app.AppViewModel.Events.ERROR
import com.mmteams91.todoapp.app.AppViewModel.Events.HIDE_PROGRESS
import com.mmteams91.todoapp.app.AppViewModel.Events.SHOW_PROGRESS
import com.mmteams91.todoapp.core.data.network.NetworkNotAvailableException
import com.mmteams91.todoapp.core.data.network.models.NetworkStatusChecker
import com.mmteams91.todoapp.core.data.socket.SocketMessagesProvider
import com.mmteams91.todoapp.core.domain.usecases.base.run
import com.mmteams91.todoapp.core.extensions.doOnFirst
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel
import com.mmteams91.todoapp.features.user.data.IUserRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor
import java.io.IOException
import javax.inject.Inject

class AppViewModel @Inject constructor(
        private val provideSocketConnectionUseCase: ProvideSocketConnectionUseCase,
        private val socketMessagesProvider: SocketMessagesProvider,
        private val userRepository: IUserRepository,
        var networkStatusChecker: NetworkStatusChecker
) : BaseViewModel() {

    private val navigateEventPublisher = BehaviorProcessor.createDefault(prepareDefaultNavigateEvent())

    fun navigateFlow(): Flowable<Screen> = navigateEventPublisher
            .compose(::wrapWithProgress)

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
//        TODO()
    }

    private fun provideSocketConnection() {
        addDisposable(provideSocketConnectionUseCase.run())
    }

    fun publishError(messageRes: Int = R.string.default_snackbar_message) {
        publishEvent(ERROR, messageRes)
    }

    fun publishError(message: CharSequence) {
        publishEvent(ERROR, message)
    }

    fun checkPermission(permission: String, onGrant: (Boolean) -> Unit) {
        publishEvent(CHECK_PERMISSION, Pair(permission, onGrant))
    }


    protected fun <T> checkNetwork(flowable: Flowable<T>,
                                   onNetworkNotAvailable: () -> Unit = ::onNetworkNotAvailable): Flowable<T> {
        return networkStatusChecker.isNetworkAvailableSinlge()
                .flatMapPublisher { isConnected ->
                    if (isConnected) flowable
                    else {
                        onNetworkNotAvailable.invoke()
                        Flowable.error(NetworkNotAvailableException())
                    }
                }
    }

    protected fun checkNetwork(completable: Completable, onNetworkNotAvailable: () -> Unit = ::onNetworkNotAvailable): Completable {
        return networkStatusChecker.isNetworkAvailableSinlge()
                .flatMapCompletable { isConnected ->
                    if (isConnected) completable
                    else {
                        onNetworkNotAvailable.invoke()
                        Completable.error(NetworkNotAvailableException())
                    }
                }
    }


    override fun parseNetworkError(throwable: Throwable) {
        if (throwable is IOException) {
            publishError(R.string.network_error_message)
        } else publishError()
    }

    fun onNetworkNotAvailable() = publishError(R.string.network_error_message)


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