package com.hadilq.guidomia.di.fragment

import androidx.fragment.app.Fragment
import com.hadilq.guidomia.core.api.FragmentFactory
import com.hadilq.guidomia.core.api.SimpleFragmentFactory
import com.hadilq.guidomia.core.api.di.AppScope
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject
import kotlin.reflect.KClass

@ContributesBinding(AppScope::class)
class FragmentFactoryImpl @Inject constructor(
  private val creators: Map<Class<out Fragment>, @JvmSuppressWildcards SimpleFragmentFactory>
) : FragmentFactory {

  override fun <T : Fragment> instantiate(clazz: KClass<T>): Fragment =
    creators[clazz.java]!!.instantiate()
}
