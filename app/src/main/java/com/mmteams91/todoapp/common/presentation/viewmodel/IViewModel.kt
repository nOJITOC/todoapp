package com.mmteams91.todoapp.common.presentation.viewmodel

import io.reactivex.Flowable

interface IViewModel {
    fun publishEvent(name: String, payload: Any? = null) =
            publishEvent(payload?.let { EventWithPayload(name, it) } ?: Event(name))

    fun publishEvent(event: Event)

    fun eventsFlow(): Flowable<Event>

    fun onCreate()

    fun onStart()

}

