package com.mmteams91.todoapp.features.synchronisation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.Event.*
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import com.mmteams91.todoapp.common.data.network.NotNeedAuth
import com.mmteams91.todoapp.common.extensions.fromJson
import com.mmteams91.todoapp.common.extensions.safeSubscribe
import com.mmteams91.todoapp.features.synchronisation.data.InputSyncCommand
import com.mmteams91.todoapp.features.synchronisation.data.InputSyncCommand.Types.AGENDA_UPDATED
import com.mmteams91.todoapp.features.synchronisation.data.InputSyncCommand.Types.SYNC_NEEDED
import com.squareup.moshi.Moshi
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.processors.lifecycleEventsFlow
import okhttp3.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketMessagesProvider @Inject constructor(
        @NotNeedAuth private val okHttpClient: OkHttpClient,
        private val moshi: Moshi
) : LifecycleOwner, ISyncProvider<InputSyncCommand> {
    private val lifecycleRegistry = LifecycleRegistry(this)
    private var lifecycleEventsDisposable: Disposable? = null
    private val messagePublisher = PublishProcessor.create<InputSyncCommand>()

    var webSocket: WebSocket? = null


    override fun syncFlow(): Flowable<List<InputSyncCommand>> = messagePublisher.buffer(300L, TimeUnit.MILLISECONDS)
            .flatMap { messages -> Flowable.just(messages.distinctBy { it.type }) }

    fun isStarted() = lifecycle.currentState == Lifecycle.State.STARTED

    fun start(url: String) {
        webSocket?.cancel()
        lifecycleRegistry.handleLifecycleEvent(ON_CREATE)
        webSocket = newWebSocket(url)
        lifecycleEventsDisposable?.dispose()
        lifecycleEventsDisposable = lifecycleEventsFlow()
                .safeSubscribe {
                    when (it) {
                        ON_START -> {
                            messagePublisher.onNext(InputSyncCommand(AGENDA_UPDATED))
                            messagePublisher.onNext(InputSyncCommand(SYNC_NEEDED))
                        }
                        ON_STOP -> start(url)
                    }
                }
    }


    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    private fun newWebSocket(url: String): WebSocket = newWebSocket(
            Request.Builder()
                    .url(url)
                    .addHeader("Origin", "https://something")
                    .build()
    )

    private fun newWebSocket(request: Request) = okHttpClient.newWebSocket(request, createListener())

    private fun createListener(): WebSocketListener {
        return object : WebSocketListener() {

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Timber.e(t)
                super.onFailure(webSocket, t, response)
            }


            override fun onOpen(webSocket: WebSocket, response: Response) {
                lifecycleRegistry.handleLifecycleEvent(ON_START)
                super.onOpen(webSocket, response)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Timber.d("onMessage : $text")
                moshi.fromJson<InputSyncCommand>(text)?.let { messagePublisher.onNext(it) }
                super.onMessage(webSocket, text)
            }


            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Timber.e("onClosed $reason")
                lifecycleRegistry.handleLifecycleEvent(ON_STOP)
                super.onClosed(webSocket, code, reason)
            }
        }
    }

    fun stop() {
        lifecycleEventsDisposable?.dispose()
        webSocket?.cancel()
        lifecycleRegistry.handleLifecycleEvent(ON_DESTROY)
    }

}