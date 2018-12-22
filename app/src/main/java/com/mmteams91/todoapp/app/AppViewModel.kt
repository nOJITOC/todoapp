package com.mmteams91.todoapp.app

import com.mmteams91.todoapp.core.presentation.viewmodel.BaseViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class AppViewModel:BaseViewModel(),IAppViewModel {


    override fun publishErrorEvent(messageRes: Int) {
        publishEvent(Events.EVENT_ERROR, messageRes)
    }

    override fun publishErrorEvent(message: CharSequence) {
        publishEvent(Events.EVENT_ERROR, message)
    }

    override fun publishShowProgressEvent() = publishEvent(Events.EVENT_SHOW_PROGRESS)

    override fun publishHideProgressEvent() = publishEvent(Events.EVENT_HIDE_PROGRESS)

    fun <T> wrapWithProgress(flowable: Flowable<T>) = flowable.doOnSubscribe {
        publishShowProgressEvent()
    }.doOnTerminate(::publishHideProgressEvent)

    fun <T> wrapWithProgress(single: Single<T>) = single.doOnSubscribe {
        publishShowProgressEvent()
    }.doFinally(::publishHideProgressEvent)

    fun wrapWithProgress(completable: Completable) = completable.doOnSubscribe {
        publishShowProgressEvent()
    }.doOnTerminate(::publishHideProgressEvent)
}