package com.hadilq.guidomia.core.api

import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty

interface ViewBindingProvider {

  fun <T : ViewBinding> viewBinding(
    owner: LifecycleOwner,
    initializer: () -> T
  ): ReadOnlyProperty<LifecycleOwner, T>
}
