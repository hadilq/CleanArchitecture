package com.hadilq.guidomia

import android.app.Application
import com.hadilq.guidomia.di.AppComponent
import com.hadilq.guidomia.di.DaggerAppComponent
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponent
import com.hadilq.guidomia.singleactivity.impl.di.SingleActivityComponentProvider
import javax.inject.Singleton

@Singleton
class CustomApplication : Application(), SingleActivityComponentProvider {

  private val component: AppComponent by lazy { DaggerAppComponent.create() }

  override val singleActivityComponentProvider: SingleActivityComponent.Builder
    get() = component.singleActivityComponentBuilder()
}