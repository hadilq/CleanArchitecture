package com.hadilq.guidomia.core.api

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

interface FragmentFactory {

  fun <T : Fragment> instantiate(clazz: KClass<T>): Fragment
}
