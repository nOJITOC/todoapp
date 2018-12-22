package com.mmteams91.todoapp.core.presentation.viewmodel

import android.arch.lifecycle.ViewModel
import com.mmteams91.todoapp.core.presentation.EVENT_PREFIX
import io.reactivex.Flowable
import io.reactivex.processors.SingleQueueProcessor

open class BaseViewModel : ViewModel(), IViewModel {
    private val eventPublisher = SingleQueueProcessor.create<Event>()

    override fun publishEvent(event: Event) {
        eventPublisher.onNext(event)
    }

    override fun eventsFlow(): Flowable<Event> {
        return eventPublisher
    }

    override fun onCreate() {
    }

    override fun onStart() {
    }


    object Events {
        const val EVENT_ERROR = EVENT_PREFIX + "Error"
        const val EVENT_SHOW_PROGRESS = EVENT_PREFIX + "Show progress"
        const val EVENT_HIDE_PROGRESS = EVENT_PREFIX + "Hide progress"

    }
}