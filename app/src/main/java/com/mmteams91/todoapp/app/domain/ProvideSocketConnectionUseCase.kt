package com.mmteams91.todoapp.app.domain

import com.mmteams91.todoapp.common.domain.usecases.base.UseCase
import com.mmteams91.todoapp.common.domain.usecases.preferences.TrackFromPrefsUseCase
import com.mmteams91.todoapp.common.extensions.EMPTY
import com.mmteams91.todoapp.common.extensions.safeSubscribe
import com.mmteams91.todoapp.features.synchronisation.InputSyncHandler
import com.mmteams91.todoapp.features.synchronisation.SocketMessagesProvider
import com.mmteams91.todoapp.features.user.data.WEB_SOCKET_URL_KEY
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProvideSocketConnectionUseCase @Inject constructor(
        private val trackFromPrefsUseCase: TrackFromPrefsUseCase,
        private val socketMessagesProvider: SocketMessagesProvider,
        private val inputSyncHandler: InputSyncHandler

) : UseCase<Unit, Disposable> {
    override fun run(requestValue: Unit): Disposable {
        inputSyncHandler.obtain(socketMessagesProvider)
        return trackFromPrefsUseCase.run(WEB_SOCKET_URL_KEY, String.EMPTY)
                .doFinally { inputSyncHandler.stop()}
                .safeSubscribe { url ->
                    if (url == String.EMPTY) {
                        socketMessagesProvider.stop()
                    } else {
                        socketMessagesProvider.start(url)
                    }
                }
    }
}
