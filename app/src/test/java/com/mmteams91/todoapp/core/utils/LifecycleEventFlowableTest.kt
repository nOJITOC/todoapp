package com.mmteams91.todoapp.core.utils

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import io.reactivex.processors.lifecycleEventsFlow
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test

class LifecycleEventFlowableTest {

    private lateinit var subscriber: TestSubscriber<Lifecycle.Event>
    private lateinit var event: Lifecycle.Event
    private lateinit var lifecycleOwner: LifecycleOwner

    private lateinit var lifecycleRegistry: LifecycleRegistry


    @Before
    fun setUp() {
        lifecycleOwner = LifecycleOwner { lifecycleRegistry }
        lifecycleRegistry = LifecycleRegistry(lifecycleOwner)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        subscriber = lifecycleOwner.lifecycleEventsFlow().test()
    }

    @Test
    fun `when lifecycle handle other event`() {
        whenLifecycleEmitEvent(Lifecycle.Event.ON_RESUME)
        thenSubscriberWentToThatEvent()
        thenSubscriberIsNotComplete()
        whenLifecycleEmitEvent(Lifecycle.Event.ON_STOP)
        thenSubscriberWentToThatEvent()
        whenLifecycleEmitEvent(Lifecycle.Event.ON_START)
        thenSubscriberWentToThatEvent()
        thenSubscriberIsNotComplete()
    }

    @Test
    fun `when lifecycle handle ON_DESTROY event `() {
        whenLifecycleEmitEvent(Lifecycle.Event.ON_DESTROY)
        thenSubscriberWentToThatEvent()
        thenSubscriberIsComplete()
    }

    @Test
    fun `when lifecycle handle same event`() {
        whenLifecycleEmitEvent(Lifecycle.Event.ON_START)
        val valuesCount = subscriber.valueCount()
        whenLifecycleEmitEvent(Lifecycle.Event.ON_START)
        subscriber.assertValueCount(valuesCount)
    }

    private fun thenSubscriberIsComplete() {
        subscriber.assertComplete()
    }

    private fun thenSubscriberIsNotComplete() {
        subscriber.assertNotComplete()
    }

    private fun thenSubscriberWentToThatEvent() {
        assert(subscriber.values().last() == event)
    }

    private fun whenLifecycleEmitEvent(event: Lifecycle.Event) {
        this.event = event
        lifecycleRegistry.handleLifecycleEvent(event)
    }


}