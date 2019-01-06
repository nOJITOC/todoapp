package com.mmteams91.todoapp.app

import com.mmteams91.todoapp.core.data.socket.SocketMessagesProvider
import com.mmteams91.todoapp.core.domain.usecases.base.UseCase
import com.mmteams91.todoapp.core.domain.usecases.preferences.TrackFromPrefsUseCase
import com.mmteams91.todoapp.core.extensions.EMPTY
import com.mmteams91.todoapp.core.extensions.safeSubscribe
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProvideSocketConnectionUseCase @Inject constructor(
        private val trackFromPrefsUseCase: TrackFromPrefsUseCase,
        private val socketMessagesProvider: SocketMessagesProvider
) : UseCase<Unit, Disposable> {
    override fun run(requestValue: Unit): Disposable {
        return trackFromPrefsUseCase.run(WEB_SOCKET_URL_KEY, String.EMPTY)
                .safeSubscribe { url ->
                    if (url.isBlank()) {
                        socketMessagesProvider.stop()
                    } else {
                        socketMessagesProvider.start(url)
                    }
                }
    }
}

private const val WEB_SOCKET_URL_KEY = "WEB_SOCKET_URL_KEY"
