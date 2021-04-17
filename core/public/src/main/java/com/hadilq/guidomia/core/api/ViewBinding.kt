package com.hadilq.guidomia.core.impl

import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
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