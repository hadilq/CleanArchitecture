package com.gitlab.sample.presentation.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.DESTROYED
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
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
            observeIfPossible()
        }
    }

    private fun observeIfPossible() {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            disposable ?: let {
                disposable = flowable.subscribe { data -> observer(data) }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        observeIfPossible()
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