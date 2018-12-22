package com.mmteams91.todoapp.core.extensions

import com.mmteams91.todoapp.core.utils.Optional
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable


fun <T, R> Flowable<out Iterable<T>>.withIterable(func: (T) -> R): Flowable<List<R>> {
    return this.flatMap { iterable ->
        Flowable.fromIterable(iterable)
                .map { func(it) }
                .toList()
                .toFlowable()
    }
}

fun <T, R> Flowable<out Iterable<T>>.withIterable(func: (T) -> R, sorted: (T, T) -> Int, filter: ((T) -> Boolean) = { true }): Flowable<List<R>> {
    return this.flatMap { iterable ->
        Flowable.fromIterable(iterable)
                .filter(filter)
                .sorted { o1, o2 -> sorted.invoke(o1, o2) }
                .map { func(it) }
                .toList()
                .toFlowable()
    }
}

fun <T> Flowable<List<T>>.toOptional(): Flowable<Optional<T>> {
    return this.flatMap {
        return@flatMap if (it.isNotEmpty()) {
            Flowable.just(Optional.of(it[0]))
        } else {
            Flowable.just(Optional.empty())
        }
    }
}

fun <T, R> Flowable<Optional<T>>.withOptional(func: (T) -> R): Flowable<Optional<R>> {
    return this.map {
        it.orNull()?.let { Optional.of(func.invoke(it)) }
                ?: Optional.empty()
    }
}


fun <T> Flowable<Optional<T>>.doWhenOptionalPresent(func: (T) -> Unit): Flowable<Optional<T>> {
    return this.doOnNext { it.orNull()?.let { func.invoke(it) } }
}

fun <T> Flowable<List<T>>.sortList(func: (T, T) -> Int): Flowable<List<T>> {
    return this.flatMap {
        Flowable.fromIterable(it)
                .toSortedList { o1, o2 -> func.invoke(o1, o2) }
                .toFlowable()
    }
}

fun <T> Flowable<Optional<T>>.toSingle(): Single<T> = map { it.get() }.firstOrError()


fun <T> Observable<T>.safeSubscribe(onError: (Throwable) -> Unit = {}, onNext: (T) -> Unit): Disposable = subscribe(onNext,onError)
fun <T> Flowable<T>.safeSubscribe(onError: (Throwable) -> Unit = {},onNext: (T) -> Unit): Disposable = subscribe(onNext,onError)

fun <T> Single<T>.safeSubscribe(onError: (Throwable) -> Unit = {}, onSuccess: (T) -> Unit): Disposable = subscribe(onSuccess, onError)


fun Completable.safeSubscribe(onError: (Throwable) -> Unit = {}, onComplete: () -> Unit): Disposable = subscribe(onComplete, onError)