package com.mmteams91.todoapp.core.presentation.viewmodel

import com.mmteams91.todoapp.core.presentation.ViewModelTestBasis
import org.junit.Before
import org.junit.Test

class BaseViewModelTest : ViewModelTestBasis<BaseViewModel>() {


    protected lateinit var events: Array<Event>

    @Before
    fun setUp() {
        subject = object : BaseViewModel() {
        }
        events = arrayOf(Event("event 1"), Event("event 2"), EventWithPayload("event 3", "payload"))
    }

    @Test
    fun `publish event`() {
        whenSubscribeToEvents()
        whenViewModelPublishEvents(*events)
        thenEventSubscriberConsumeValues(*events)
        thenEventSubscriberNotComplete()
    }

    @Test
    fun `publish event before subscribe`() {
        whenViewModelPublishEvents(*events)
        whenSubscribeToEvents()
        thenEventSubscriberConsumeValues(*events)
        thenEventSubscriberNotComplete()
    }

    private fun whenViewModelPublishEvents(vararg events: Event) {
        events.forEach { subject.publishEvent(it) }
    }

    private fun whenSubscribeToEvents() = subscribeToEvents()
}