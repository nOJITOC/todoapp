package com.mmteams91.todoapp.core.data.socket

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import com.mmteams91.todoapp.core.data.network.NotNeedAuth
import com.mmteams91.todoapp.core.data.socket.SocketMessage.Types.AGENDA_UPDATED
import com.mmteams91.todoapp.core.data.socket.SocketMessage.Types.SYNC_NEEDED
import com.mmteams91.todoapp.core.extensions.fromJson
import com.mmteams91.todoapp.core.extensions.safeSubscribe
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
) : LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)
    private var eventsDisposable: Disposable? = null
    private val messagePublisher = PublishProcessor.create<SocketMessage>()

    var webSocket: WebSocket? = null

    fun start(url: String) {
        webSocket?.cancel()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        webSocket = newWebSocket(url)
        eventsDisposable?.dispose()
        eventsDisposable = lifecycleEventsFlow()
                .safeSubscribe {
                    when (it) {
                        Lifecycle.Event.ON_START -> {
                            messagePublisher.onNext(SocketMessage(AGENDA_UPDATED))
                            messagePublisher.onNext(SocketMessage(SYNC_NEEDED))
                        }
                        Lifecycle.Event.ON_STOP -> start(url)
                    }
                }
    }

    fun messages(): Flowable<SocketMessage> = messagePublisher.buffer(200L, TimeUnit.MILLISECONDS)
            .flatMap { messages -> Flowable.fromIterable(messages).distinct { it.type } }

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
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
                super.onOpen(webSocket, response)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Timber.d("onMessage : $text")
                moshi.fromJson<SocketMessage>(text)?.let { messagePublisher.onNext(it) }
                super.onMessage(webSocket, text)
            }


            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Timber.e("onClosed $reason")
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
                super.onClosed(webSocket, code, reason)
            }
        }
    }

    fun stop() {
        eventsDisposable?.dispose()
        webSocket?.cancel()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

}