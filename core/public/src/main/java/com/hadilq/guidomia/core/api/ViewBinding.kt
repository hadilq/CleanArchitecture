/**
 * Copyright 2021 Hadi Lashkari Ghouchani

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hadilq.guidomia.core.api

import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : ViewBinding> LifecycleOwner.viewBinding(
  initializer: () -> T
): ReadOnlyProperty<LifecycleOwner, T> = ViewBindingPropertyDelegate(this, initializer)

class ViewBindingPropertyDelegate<T : ViewBinding>(
  owner: LifecycleOwner,
  private val initializer: () -> T
) : ReadOnlyProperty<LifecycleOwner, T>, LifecycleObserver {

  private var _value: T? = null

  init {
    owner.lifecycle.addObserver(this)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onDestroy() {
    _value = null
  }

  override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): T {
    if (_value == null) {

      // This must be on the main thread only
      if (Looper.myLooper() != Looper.getMainLooper()) {
        throw IllegalThreadStateException(
          "This cannot be called from other threads. " +
            "It should be on the main thread only."
        )
      }

      _value = initializer()
    }
    return _value!!
  }
}