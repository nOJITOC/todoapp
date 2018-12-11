package com.mmteams91.todoapp.core.presentation.viewmodel

import android.support.annotation.StringRes
import io.reactivex.Flowable

interface IViewModel {
    fun publishEvent(name: String, payload: Any? = null) =
            publishEvent(payload?.let { EventWithPayload(name, it) } ?: Event(name))

    fun publishEvent(event: Event)

    fun publishErrorEvent(@StringRes messageRes: Int)

    fun publishErrorEvent(message: CharSequence)

    fun eventsFlow(): Flowable<Event>
}

