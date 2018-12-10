package com.gitlab.sample.presentation.common

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Lifecycle.State.DESTROYED
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

class RxLifecycleHandler<T>(
        owner: LifecycleOwner,
        private val flowable: Flowable<T>,
        private val observer: (T) -> Unit
) : LifecycleObserver {
    private val lifecycle = owner.lifecycle
    private var disposable: Disposable? = null

    init {
        if (lifecycle.currentState != DESTROYED) {
            owner.lifecycle.addObserver(this)
            observerIfPossible()
        }
    }

    private fun observerIfPossible() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            disposable ?: let {
                disposable = flowable.subscribe { data -> observer(data) }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        observerIfPossible()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        disposable?.dispose()
        disposable = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        lifecycle.removeObserver(this)
    }
}