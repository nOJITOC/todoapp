package com.mmteams91.todoapp.app

import android.support.annotation.StringRes
import com.mmteams91.todoapp.app.AppViewModel.Events.HIDE_PROGRESS
import com.mmteams91.todoapp.app.AppViewModel.Events.SHOW_PROGRESS
import com.mmteams91.todoapp.core.presentation.ViewModelTestBasis
import com.mmteams91.todoapp.core.presentation.viewmodel.Event
import com.mmteams91.todoapp.core.presentation.viewmodel.EventWithPayload
import io.reactivex.processors.PublishProcessor
import org.junit.Before
import org.junit.Test

class AppViewModelTest:ViewModelTestBasis<AppViewModel>(){

    @Before
    fun setUp() {
        subject = AppViewModel()
        subscribeToEvents()
    }
    @Test
    fun `publish error events`() {
        val errorMessage = "error"
        val errorMessageRes = 2
        val errors = arrayOf(errorMessage, errorMessageRes)
        whenViewModelPublishError(errorMessage)
        whenViewModelPublishError(errorMessageRes)
        thenEventSubscriberConsumeValues(*errors.map { EventWithPayload(AppViewModel.Events.ERROR, it) }.toTypedArray())
        thenEventSubscriberNotComplete()
    }

    @Test
    fun `publish SHOW_PROGRESS when subscribe with wrapWithProgress`(){
        //GIVEN
        val flowable = PublishProcessor.create<Any>()
        flowable.compose(subject::wrapWithProgress).test()
        thenEventSubscriberConsumeValues(Event(SHOW_PROGRESS))
        thenEventSubscriberNotComplete()
    }

    @Test
    fun `publish HIDE_PROGRESS when subscriber with wrapWithProgress has error`(){
        //GIVEN
        val flowable = PublishProcessor.create<Any>()
        flowable.compose(subject::wrapWithProgress).test()
        //WHEN
        flowable.onError(RuntimeException())
        //THEN
        thenEventSubscriberConsumeValues(Event(SHOW_PROGRESS), Event((HIDE_PROGRESS)))
        thenEventSubscriberNotComplete()
    }


    @Test
    fun `publish HIDE_PROGRESS when subscriber with wrapWithProgress completed`(){
        //GIVEN
        val flowable = PublishProcessor.create<Any>()
        flowable.compose(subject::wrapWithProgress).test()
        //WHEN
        flowable.onComplete()
        //THEN
        thenEventSubscriberConsumeValues(Event(SHOW_PROGRESS), Event((HIDE_PROGRESS)))
        thenEventSubscriberNotComplete()
    }


    @Test
    fun `publish HIDE_PROGRESS when subscriber with wrapWithProgress disposed`(){
        //GIVEN
        val flowable = PublishProcessor.create<Any>()
        val flowSubscriber = flowable.compose(subject::wrapWithProgress).test()
        //WHEN
        flowSubscriber.dispose()
        //THEN
        thenEventSubscriberConsumeValues(Event(SHOW_PROGRESS), Event((HIDE_PROGRESS)))
        thenEventSubscriberNotComplete()
    }

    private fun whenViewModelPublishError(message: CharSequence) {
        subject.publishError(message)
    }

    private fun whenViewModelPublishError(@StringRes messageRes: Int) {
        subject.publishError(messageRes)
    }

}