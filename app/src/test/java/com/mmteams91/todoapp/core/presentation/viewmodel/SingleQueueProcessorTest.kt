package com.mmteams91.todoapp.core.presentation.viewmodel

import io.reactivex.processors.SingleQueueProcessor
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before

import org.junit.Test

class SingleQueueProcessorTest {
    private lateinit var subject: SingleQueueProcessor<Int>
    @Before
    fun setUp() {
        subject = SingleQueueProcessor.create()
    }


    @Test
    fun `emit values before subscribe`() {
        val values = intArrayOf(1, 2, 3)
        whenProducerEmmitValues(*values)
        thenObserverShouldConsumeValues(subject.test(), *values)
    }

    @Test
    fun `emit values after subscribe`() {
        val values = intArrayOf(1, 2, 3)
        val observer = subject.test()
        whenProducerEmmitValues(*values)
        thenObserverShouldConsumeValues(observer, *values)
    }

    @Test
    fun `subscribe multiple time`() {
        val first = whenProducerSubscribe()
        val second = whenProducerSubscribe()
        whenProducerEmmitValues(2, 3)
        thenObserverShouldComplete(first)
        thenObserverShouldConsumeValues(second, 2, 3)
        thenObserverShouldNotComplete(second)
    }

    private fun whenProducerSubscribe(): TestSubscriber<Int> {
        return subject.test()
    }

    private fun whenProducerEmmitValues(vararg values: Int) {
        values.forEach { subject.onNext(it) }
    }

    private fun thenObserverShouldConsumeValues(observer: TestSubscriber<Int>, vararg values: Int) {
        observer.assertValues(*values.toTypedArray())
    }

    private fun thenObserverShouldComplete(observer: TestSubscriber<Int>) {
        observer.assertComplete()
    }

    private fun thenObserverShouldNotComplete(observer: TestSubscriber<Int>) {
        observer.assertNotComplete()
    }
}