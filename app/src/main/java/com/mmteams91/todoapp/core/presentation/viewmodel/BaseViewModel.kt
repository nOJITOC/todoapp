package com.mmteams91.todoapp.core.presentation.viewmodel

import android.arch.lifecycle.ViewModel
import com.mmteams91.todoapp.core.presentation.EVENT_PREFIX
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel.Events.EVENT_ERROR
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel.Events.EVENT_HIDE_PROGRESS
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel.Events.EVENT_SHOW_PROGRESS
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.SingleQueueProcessor

open class BaseViewModel : ViewModel(), IViewModel {
    private val eventPublisher = SingleQueueProcessor.create<Event>()

    override fun publishEvent(event: Event) {
        eventPublisher.onNext(event)
    }

    override fun eventsFlow(): Flowable<Event> {
        return eventPublisher
    }

    override fun publishErrorEvent(messageRes: Int) {
        publishEvent(EVENT_ERROR, messageRes)
    }

    override fun publishErrorEvent(message: CharSequence) {
        publishEvent(EVENT_ERROR, message)
    }

    fun publishShowProgressEvent() = publishEvent(EVENT_SHOW_PROGRESS)

    fun publishHideProgressEvent() = publishEvent(EVENT_HIDE_PROGRESS)

    fun <T> wrapWithProgress(flowable: Flowable<T>) = flowable.doOnSubscribe {
        publishShowProgressEvent()
    }.doOnTerminate(::publishHideProgressEvent)

    fun <T> wrapWithProgress(single: Single<T>) = single.doOnSubscribe {
        publishShowProgressEvent()
    }.doFinally(::publishHideProgressEvent)

    fun wrapWithProgress(completable: Completable) = completable.doOnSubscribe {
        publishShowProgressEvent()
    }.doOnTerminate(::publishHideProgressEvent)


    object Events {
        const val EVENT_ERROR = EVENT_PREFIX + "Error"
        const val EVENT_SHOW_PROGRESS = EVENT_PREFIX + "Show progress"
        const val EVENT_HIDE_PROGRESS = EVENT_PREFIX + "Hide progress"

    }
}