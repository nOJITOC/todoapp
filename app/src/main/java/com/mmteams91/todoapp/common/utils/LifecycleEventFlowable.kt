package io.reactivex.processors

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.Flowable
import org.reactivestreams.Subscriber
import timber.log.Timber

class LifecycleEventFlowable internal constructor(lifecycle: Lifecycle) : Flowable<Lifecycle.Event>(), LifecycleObserver {
    private val processor = BehaviorProcessor.create<Lifecycle.Event>()

    internal constructor(lifecycleOwner: LifecycleOwner) : this(lifecycleOwner.lifecycle)

    init {
        lifecycle.addObserver(this)

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    internal fun onLifecycleEvent(owner: LifecycleOwner, event: Lifecycle.Event) {
        Timber.e("owner = $owner, event - $event")
        processor.onNext(event)
        if (event == Lifecycle.Event.ON_DESTROY) {
            owner.lifecycle.removeObserver(this)
            processor.onComplete()
        }
    }

    override fun subscribeActual(s: Subscriber<in Lifecycle.Event>?) {
        processor.subscribeActual(s)
    }

}

fun LifecycleOwner.lifecycleEventsFlow() = LifecycleEventFlowable(this)

fun Lifecycle.eventsFlow() = LifecycleEventFlowable(this)