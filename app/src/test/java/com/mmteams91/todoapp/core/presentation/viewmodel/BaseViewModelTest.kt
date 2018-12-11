package com.mmteams91.todoapp.core.presentation.viewmodel

import android.support.annotation.StringRes
import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel.Events.EVENT_ERROR
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

class BaseViewModelTest {
    private lateinit var subject: BaseViewModel
    private lateinit var events: Array<Event>
    private lateinit var subscriber: TestSubscriber<Event>

    @Before
    fun setUp() {
        subject = BaseViewModel()
        events = arrayOf(Event("event 1"), Event("event 2"), EventWithPayload("event 3", "payload"))
    }

    @Test
    fun `publish event`() {
        whenViewModelPublishEvents(*events)
        whenSubscribeToEvents()
        thenSubscriberConsumeValues(*events)
        thenSubscriberNotComplete()
    }

    @Test
    fun `publish event before subscribe`() {
        whenViewModelPublishEvents(*events)
        whenSubscribeToEvents()
        thenSubscriberConsumeValues(*events)
    }

    @Test
    fun `publish error events`() {
        val errorMessage = "error"
        val errorMessageRes = 2
        val errors = arrayOf(errorMessage, errorMessageRes)
        whenViewModelPublishError(errorMessage)
        whenViewModelPublishError(errorMessageRes)
        whenSubscribeToEvents()
        thenSubscriberConsumeValues(*errors.map { EventWithPayload(EVENT_ERROR, it) }.toTypedArray())
        thenSubscriberNotComplete()
    }

    private fun whenSubscribeToEvents() {
        subscriber = subject.eventsFlow().test()
    }


    private fun whenViewModelPublishError(message: CharSequence) {
        subject.publishErrorEvent(message)
    }

    private fun whenViewModelPublishError(@StringRes messageRes: Int) {
        subject.publishErrorEvent(messageRes)
    }

    private fun whenViewModelPublishEvents(vararg events: Event) {
        events.forEach { subject.publishEvent(it) }
    }

    private fun thenSubscriberConsumeValues(vararg events: Event) {
        subscriber.assertValues(*events)
    }

    private fun thenSubscriberNotComplete() {
        subscriber.assertNotComplete()
    }


}