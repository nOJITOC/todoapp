package com.mmteams91.todoapp.core.presentation

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.Event.*
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import com.mmteams91.todoapp.core.presentation.view.DisposableBinder
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DisposableBinderTest {

    private lateinit var subject: DisposableBinder
    lateinit var lifecycleOwner: LifecycleOwner
    private lateinit var registry: LifecycleRegistry
    private lateinit var disposable: Disposable

    @Before
    fun setUp() {
        disposable = Disposables.fromRunnable { }

        lifecycleOwner = LifecycleOwner { registry }
        registry = LifecycleRegistry(lifecycleOwner)
        subject = DisposableBinder.on(lifecycleOwner)
    }

    @Test
    fun `bind on ON_CREATE`() {
        whenBindOn(ON_CREATE)
        thenDisposedOn(ON_DESTROY)
    }


    @Test
    fun `bind on ON_START`() {
        whenBindOn(ON_START)
        thenDisposedOn(ON_STOP)
    }


    @Test
    fun `bind on ON_RESUME`() {
        whenBindOn(ON_RESUME)
        thenDisposedOn(ON_PAUSE)
    }


    @Test
    fun `bind on ON_PAUSE`() {
        whenBindOn(ON_PAUSE)
        thenDisposedOn(ON_DESTROY)
    }


    @Test
    fun `bind on ON_STOP`() {
        whenBindOn(ON_STOP)
        thenDisposedOn(ON_DESTROY)
    }


    @Test
    fun `bind on ON_DESTROY`() {
        whenBindOn(ON_DESTROY)
        thenDisposedOn(ON_DESTROY)
    }

    @Test
    fun `bind until ON_CREATE`() {
        whenBindUntil(ON_CREATE)
        thenDisposedOn(ON_CREATE)
    }


    @Test
    fun `bind until ON_START`() {
        whenBindUntil(ON_START)
        thenDisposedOn(ON_START)
    }


    @Test
    fun `bind until ON_RESUME`() {
        whenBindUntil(ON_RESUME)
        thenDisposedOn(ON_RESUME)
    }


    @Test
    fun `bind until ON_PAUSE`() {
        whenBindUntil(ON_PAUSE)
        thenDisposedOn(ON_PAUSE)
    }


    @Test
    fun `bind until ON_STOP`() {
        whenBindUntil(ON_STOP)
        thenDisposedOn(ON_STOP)
    }


    @Test
    fun `bind until ON_DESTROY`() {
        whenBindUntil(ON_DESTROY)
        thenDisposedOn(ON_DESTROY)
    }

    private fun whenBindUntil(event: Lifecycle.Event) {
        subject.bindToLifecycleUntil(event, disposable)
    }

    private fun whenBindOn(event: Lifecycle.Event) {
        Lifecycle.Event.values().asSequence()
                .filter { it <= event }
                .forEach {
                    registry.handleLifecycleEvent(it)
                }
        subject.bindToLifecycle(disposable)
    }

    private fun thenDisposedOn(event: Lifecycle.Event) {

        Lifecycle.Event.values().asSequence()
                .filter { it >= subject.lastEvent && it != ON_ANY }
                .forEach {
                    registry.handleLifecycleEvent(it)
                    val condition = if (it < event) !disposable.isDisposed else disposable.isDisposed
                    assertTrue(condition)
                }
    }


    @Test
    fun bindToLifecycleUntil() {

    }
}