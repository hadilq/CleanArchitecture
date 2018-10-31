/***
 * Copyright 2018 Hadi Lashkari Ghouchani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * */
package com.gitlab.sample.presentation.common

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel : ViewModel() {
    val actionSteam = PublishSubject.create<Action>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    protected val viewState: MutableLiveData<ViewState> = MutableLiveData()
    private var lastViewState: ViewState? = null

    /**
     * To disable what they call "Always up to date data" feature, we need to subscribe once per View instance to
     * the LiveData and also use lastViewState.
     * */
    fun register(owner: LifecycleOwner, observer: Observer<ViewState>): Disposable =
            Observable.fromPublisher(LiveDataReactiveStreams.toPublisher(owner, viewState))
                    .toFlowable(BackpressureStrategy.BUFFER)
                    .filter { val new = lastViewState != it;lastViewState = it;new }
                    .subscribe(observer::onNext)

    protected fun Disposable.track() {
        compositeDisposable.add(this)
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
