package com.hadilq.guidomia.core.impl

import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import com.hadilq.guidomia.core.api.ViewBindingProvider
import com.hadilq.guidomia.core.api.di.AppScope
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@ContributesBinding(AppScope::class)
class ViewBindingProviderImpl @Inject constructor() : ViewBindingProvider {

  override fun <T : ViewBinding> viewBinding(
    owner: LifecycleOwner,
    initializer: () -> T
  ): ReadOnlyProperty<AppCompatActivity, T> = ViewBindingPropertyDelegate(owner, initializer)
}

class ViewBindingPropertyDelegate<T : ViewBinding>(
  private val owner: LifecycleOwner,
  private val initializer: () -> T
) : ReadOnlyProperty<AppCompatActivity, T>, LifecycleObserver {

  private var _value: T? = null

  init {
    owner.lifecycle.addObserver(this)
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
  fun onDestroy() {
    _value = null
  }

  override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
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