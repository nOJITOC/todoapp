package io.reactivex.processors

import io.reactivex.Flowable
import io.reactivex.internal.queue.SpscLinkedArrayQueue
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class SingleQueueProcessor<T> private constructor() : FlowableProcessor<T>() {
    private val publisher = PublishProcessor.create<T>()


    private val queue: SpscLinkedArrayQueue<T> = SpscLinkedArrayQueue(Flowable.bufferSize())

    override fun hasThrowable(): Boolean {
        return publisher.hasThrowable()
    }

    override fun onSubscribe(s: Subscription) {
        publisher.onSubscribe(s)
    }

    override fun subscribeActual(s: Subscriber<in T>?) {
        if (hasSubscribers()) {
            publisher.subscribers.getAndSet(emptyArray()).forEach { it.onComplete() }
        }
        publisher.subscribeActual(s)
        while(!queue.isEmpty){
            publisher.onNext(queue.poll()!!)
        }
    }

    override fun hasSubscribers(): Boolean {
        return publisher.hasSubscribers()
    }

    override fun onComplete() {
        publisher.onComplete()
    }


    override fun onError(e: Throwable) {
        publisher.onError(e)
    }

    override fun getThrowable(): Throwable? {
        return publisher.throwable
    }


    override fun onNext(t: T) {
        if (publisher.hasSubscribers()) publisher.onNext(t) else queue.offer(t)
    }

    override fun hasComplete(): Boolean {
        return publisher.hasComplete()
    }

    companion object {
        fun <T> create() = SingleQueueProcessor<T>()
    }
}