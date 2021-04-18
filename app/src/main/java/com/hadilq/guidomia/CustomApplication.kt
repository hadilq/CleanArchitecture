package com.hadilq.guidomia

import android.app.Application
import com.hadilq.guidomia.core.api.di.AppScope
import com.hadilq.guidomia.core.api.di.SingleIn
import com.hadilq.guidomia.di.AppComponent
import com.hadilq.guidomia.di.DaggerAppComponent
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponent
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponentProvider

@SingleIn(AppScope::class)
class CustomApplication : Application(), SingleActivityComponentProvider {

  private val component: AppComponent by lazy {
    DaggerAppComponent.builder()
      .application(this)
      .build()
  }

  override val singleActivityComponentProvider: SingleActivityComponent.Builder
    get() = component.singleActivityComponentBuilder()
}