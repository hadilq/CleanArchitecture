package com.hadilq.guidomia.di

import android.content.Context
import com.hadilq.guidomia.CustomApplication
import dagger.Binds
import dagger.Module

@Module
interface AppModule {

  @Binds
  fun provideContext(app: CustomApplication): Context
}
