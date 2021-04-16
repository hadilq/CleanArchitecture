package com.hadilq.guidomia.core.api

import androidx.fragment.app.Fragment
import dagger.MapKey
import java.lang.annotation.Documented
import kotlin.reflect.KClass

@Documented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
@MapKey
annotation class FragmentKey(val value: KClass<out Fragment>)
