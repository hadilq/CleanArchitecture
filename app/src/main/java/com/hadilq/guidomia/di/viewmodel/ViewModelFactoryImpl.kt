package com.hadilq.guidomia.di.viewmodel

import androidx.lifecycle.ViewModel
import com.hadilq.guidomia.core.api.SimpleViewModelFactory
import com.hadilq.guidomia.core.api.ViewModelFactory
import com.hadilq.guidomia.core.api.di.AppScope
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class ViewModelFactoryImpl @Inject constructor(
  private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards SimpleViewModelFactory>
) : ViewModelFactory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T =
    creators[modelClass]!!.create() as T
}
